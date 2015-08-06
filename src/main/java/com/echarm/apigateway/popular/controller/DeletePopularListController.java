package com.echarm.apigateway.popular.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.popular.error.PopularListExceptionFactory;
import com.echarm.apigateway.popular.model.PopularArticle;
import com.echarm.apigateway.popular.model.PopularArticleList;
import com.echarm.apigateway.popular.model.PopularDoctor;
import com.echarm.apigateway.popular.model.PopularDoctorList;
import com.echarm.apigateway.popular.model.PopularQA;
import com.echarm.apigateway.popular.model.PopularQAList;
import com.echarm.apigateway.popular.repository.PopularArticleListRepository;
import com.echarm.apigateway.popular.repository.PopularDoctorListRepository;
import com.echarm.apigateway.popular.repository.PopularQAListRepository;
import com.echarm.apigateway.popular.response.IdStatusListResponse;
import com.echarm.apigateway.popular.response.PopularArticleListResponseFactory;
import com.echarm.apigateway.popular.response.PopularDoctorListResponseFactory;
import com.echarm.apigateway.popular.response.PopularQAListResponseFactory;
import com.echarm.apigateway.popular.util.PopularListDocumentId;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class DeletePopularListController {

    @Autowired
    private PopularArticleListRepository articleListRepository;

    @Autowired
    private PopularDoctorListRepository doctorListRepository;

    @Autowired
    private PopularQAListRepository qaListRepository;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.DELETE)
    public IdStatusListResponse deletePopularArticleList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {

        if (articleListRepository == null) {
            throw PopularListExceptionFactory.getServerProblemException("Popular article list repository should not be null!");
        }

        // validate JSON body
        if (category == null || category.equals("")) {
            throw PopularListExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        // check id_list
        String[] idArr = CommaDelimitedStringParser.parse(idList);

        if (idArr == null || idArr.length == 0) {
            throw PopularListExceptionFactory.getServerProblemException("Id array should not be null or has size 0!");
        }

        // create list for delete
        Map<String, PopularArticle> map = new HashMap<String, PopularArticle>();
        for (String id : idArr) {
            if (id == null || id.equals("")) {
                continue;
            }

            PopularArticle article = new PopularArticle().setArticleId(id).setArticleTitle("FOR EXAMINATION");
            map.put(id, article);
        }

        PopularArticleList deleteList = new PopularArticleList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularArticleListId(category))
                                                .setArticleMap(map);

        PopularArticleList result = articleListRepository.deletePopularArticleList(deleteList);

        if (result == null || result.getArticleMap() == null) {
            throw PopularListExceptionFactory.getServerProblemException("Result popular article list should not be null or have null map!");
        }

        return PopularArticleListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.DELETE)
    public IdStatusListResponse deletePopularDoctorList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {

        if (doctorListRepository == null) {
            throw PopularListExceptionFactory.getServerProblemException("Popular doctor list repository should not be null!");
        }

        if (category == null || category.equals("")) {
            throw PopularListExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        // check id_list
        String[] idArr = CommaDelimitedStringParser.parse(idList);

        if (idArr == null || idArr.length == 0) {
            throw PopularListExceptionFactory.getServerProblemException("Id array should not be null or has size 0!");
        }

        // create list for delete
        Map<String, PopularDoctor> map = new HashMap<String, PopularDoctor>();
        for (String id : idArr) {
            if (id == null || id.equals("")) {
                continue;
            }

            PopularDoctor doctor = new PopularDoctor().setDoctorId(id).setDoctorName("FOR EXAMINATION");
            map.put(id, doctor);
        }

        PopularDoctorList deleteList = new PopularDoctorList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularDoctorListId(category))
                                                .setDoctorMap(map);

        PopularDoctorList result = doctorListRepository.deleteDoctorList(deleteList);

        if (result == null || result.getDoctorMap() == null) {
            throw PopularListExceptionFactory.getServerProblemException("Result popular doctor list should not be null or have null map!");
        }
        return PopularDoctorListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.DELETE)
    public IdStatusListResponse deletePopularQAList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {

        if (qaListRepository == null) {
            throw PopularListExceptionFactory.getServerProblemException("Popular qa list repository should not be null!");
        }

        if (category == null || category.equals("")) {
            throw PopularListExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        // check id_list
        String[] idArr = CommaDelimitedStringParser.parse(idList);

        if (idArr == null || idArr.length == 0) {
            throw PopularListExceptionFactory.getServerProblemException("Id array should not be null or has size 0!");
        }

        // create list for delete
        Map<String, PopularQA> map = new HashMap<String, PopularQA>();
        for (String id : idArr) {
            if (id == null || id.equals("")) {
                continue;
            }

            PopularQA qa = new PopularQA().setQuestionId(id).setQuestionerName("FOR EXAMINATION");
            map.put(id, qa);
        }

        PopularQAList deleteList = new PopularQAList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularQAListId(category))
                                                .setQaMap(map);

        PopularQAList result = qaListRepository.deleteQAList(deleteList);

        if (result == null || result.getQaMap() == null) {
            throw PopularListExceptionFactory.getServerProblemException("Result popular qa list should not be null or have null map!");
        }
        return PopularQAListResponseFactory.getIdStatusListResponse(result);
    }
}
