package com.echarm.apigateway.security.controller;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.AdminAccount;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.model.UserInfo;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.UserType;
import com.echarm.apigateway.security.model.ObjectSummary;
import com.echarm.apigateway.security.service.ObjectManagementService;
import com.echarm.apigateway.security.service.UserDetailsImpl;

@RestController
public class AccountStickerController {

    @Autowired
    private AccountRepositoryService repository;

    @Autowired
    private ObjectManagementService objManager;

    @Value("${cloud.aws.s3.bucket.sticker}")
    private String stickerBucket;

    @RequestMapping(value = "/me/stickers", method = RequestMethod.POST)
    public Account uploadMySticker(
            Authentication auth,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }
        if (objManager == null) {
            throw new ServerSideProblemException("Object manager null");
        }

        if (auth == null) {
            throw new ServerSideProblemException("auth null");
        }
        Object user = auth.getPrincipal();
        if (user == null || !(user instanceof UserDetailsImpl)) {
            throw new ServerSideProblemException("Authentication object should be nonnull and has type UserDetailsImpl");
        }
        Account currUserAccount = ((UserDetailsImpl) user).getAccount();
        if (currUserAccount == null || currUserAccount.getAccountId() == null) {
            throw new ServerSideProblemException("Authentication object should contain a valid account object");
        }

        /*
         * validate uploaded image
         */
        if (file == null) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("File Field: file", "body"));
            InvalidParameterException exception = new InvalidParameterException("No file contained in the request body!");
            exception.setErrorBody(body);
            throw exception;
        }
        if (file.getContentType() == null || !file.getContentType().contains("image")) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("File Field: file", "body"));
            InvalidParameterException exception = new InvalidParameterException("File should be an image!");
            exception.setErrorBody(body);
            throw exception;
        }
        if (file.getOriginalFilename() == null) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("File Field: file", "body"));
            InvalidParameterException exception = new InvalidParameterException("File should have nonnull name!");
            exception.setErrorBody(body);
            throw exception;
        }

        System.out.println("Content type: " + file.getContentType());
        System.out.println("Name: " + file.getName());
        System.out.println("Original name: " + file.getOriginalFilename());
        System.out.println("File size: " + file.getSize());

        Account account = new Account();
        account.setUserType(UserType.ARBITRARY);
        account.setAccountId(currUserAccount.getAccountId());

        AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(account);
        List<Account> results = repository.query(specification);
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }
        if (results.size() != 1) {
            throw new ServerSideProblemException("The result (List<Account>) size should be 1");
        }

        Account accountInDb = results.get(0);
        if (accountInDb == null || accountInDb.getAccountId() == null) {
            throw new ServerSideProblemException("Account in db should not be null or have null ID!");
        }

        // get old sticker url
        String oldStickerUrl = null;
        if (accountInDb.getUserInfo() != null) {
            oldStickerUrl = accountInDb.getUserInfo().getStickerUrl();
        }

        // assemble sticker filename
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String stickerFilename = String.format("%s%s", accountInDb.getAccountId(), ext == null ? "" : "." + ext);

        // upload new sticker
        MultipartFile[] fileArr = { file };
        String[] keyArr = { stickerFilename };
        List<ObjectSummary> objSummaries = objManager.upload(stickerBucket, fileArr, keyArr);
        if (objSummaries == null || objSummaries.size() != 1) {
            throw new ServerSideProblemException("Object summaries should have size 1!");
        }
        ObjectSummary summary = objSummaries.get(0);
        if (summary == null || summary.getUrl() == null) {
            throw new ServerSideProblemException("Summarys should be nonnull and have url!");
        }

        // old url not equals the new one, delete the old sticker (if old sticker url not null) and update
        if (!summary.getUrl().equals(oldStickerUrl)) {

            // delete old sticker
            if (oldStickerUrl != null) {
                String oldStickerKey = objManager.getKey(new ObjectSummary().setFileName(null).setUrl(oldStickerUrl));
                if (oldStickerKey != null) {
                    ObjectSummary oldStickerSummary = objManager.delete(stickerBucket, oldStickerKey);
                    System.out.println(String.format("Delete old sticker %s: %s",
                            oldStickerUrl, oldStickerSummary == null ? "fail" : "success"));
                }
            }

            // update new sticker url here
            accountInDb = updateNewStickerUrl(accountInDb, summary.getUrl());

            if (accountInDb == null) {
                throw new ServerSideProblemException("Updated account should not be null!");
            }
        }

        // set password/salt to null
        accountInDb.setPassword(null);
        accountInDb.setSalt(null);
        return accountInDb;
    }

    private Account updateNewStickerUrl(Account accountInDb, String newStickerUrl) throws Exception {

        Account updateAccount = null;

        if (accountInDb instanceof UserAccount) {
            updateAccount = new UserAccount();
            updateAccount.setAccountId(accountInDb.getAccountId());
            UserInfo info = new UserInfo();
            info.setStickerUrl(newStickerUrl);
            updateAccount.setUserInfo(info);
        } else if (accountInDb instanceof AdminAccount) {
            updateAccount = new AdminAccount();
            updateAccount.setAccountId(accountInDb.getAccountId());
            UserInfo info = new UserInfo();
            info.setStickerUrl(newStickerUrl);
            updateAccount.setUserInfo(info);
        } else if (accountInDb instanceof DoctorAccount) {
            updateAccount = new DoctorAccount();
            updateAccount.setAccountId(accountInDb.getAccountId());
            DoctorInfo info = new DoctorInfo();
            info.setStickerUrl(newStickerUrl);

            if (accountInDb.getUserInfo() == null ||
                !(accountInDb.getUserInfo() instanceof DoctorInfo)) {
                throw new ServerSideProblemException("Account in db should have nonnull doctorinfo!");
            }

            Category category = ((DoctorInfo) accountInDb.getUserInfo()).getCategory();
            if (category == null) {
                throw new ServerSideProblemException("Account in db should have nonnull category!");
            }
            info.setCategory(category);
            ((DoctorAccount) updateAccount).setUserInfo(info);
        } else {
            throw new ServerSideProblemException("Account in db has the wrong type!");
        }

        return repository.updateAccount(updateAccount);
    }
}
