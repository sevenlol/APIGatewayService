package com.echarm.apigateway.popular.util;

/*
 * FIXME remove this and store category list in db
 */
public class PopularCategoryList {

    public static enum ArticleCategory {
        cosmeticsurgery, plasticsurgery, gynecology, obstetrics, dermatology, pediatrics, others;
    }

    public static enum DoctorCategory {
        PGY, plasticsurgeon, cosmeticsurgeon, dermatologist, OBSGYN, pediatrician, others;
    }

    public static enum QACategory {
        cosmeticsurgery, plasticsurgery, gynecology, obstetrics, dermatology, pediatrics, others;
    }
}
