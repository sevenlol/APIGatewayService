package com.echarm.apigateway.popular.repository;

import com.echarm.apigateway.popular.model.PopularQAList;

//FIXME think about the interface
public interface PopularQAListRepository {

    public PopularQAList createQAList(PopularQAList qaList) throws Exception;
    public PopularQAList updateQAList(PopularQAList qaList) throws Exception;
    public PopularQAList deleteQAList(PopularQAList qaList) throws Exception;
    public PopularQAList readQAList(PopularQAList qaList) throws Exception;
}
