package com.echarm.apigateway.popular.controller;

import java.util.HashMap;
import java.util.List;
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
import com.echarm.apigateway.popular.repository.PopularArticleListRepository;
import com.echarm.apigateway.popular.response.IdStatusListResponse;
import com.echarm.apigateway.popular.response.PopularArticleListResponseFactory;
import com.echarm.apigateway.popular.response.PopularDoctorResponseWrapper;
import com.echarm.apigateway.popular.response.PopularQAResponseWrapper;
import com.echarm.apigateway.popular.util.PopularListDocumentId;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class DeletePopularListController {

    @Autowired
    private PopularArticleListRepository articleListRepository;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.DELETE)
    public IdStatusListResponse updatePopularArticleList(
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
    public List<PopularDoctorResponseWrapper> updatePopularDoctorList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.DELETE)
    public List<PopularQAResponseWrapper> updatePopularQAList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) {
        return null;
    }
}
