package com.echarm.apigateway.popular.util;

/*
 * FIXME remove this and store category list in db
 */
public class PopularCategoryList {

    public static enum ArticleCategory {
        cosmeticsurgery, plasticsurgery, gynecology, obstetrics, dermatology, pediatrics, oncology_consultation, others;
    }

    public static enum DoctorCategory {
        PGY, plasticsurgeon, cosmeticsurgeon, dermatologist, OBSGYN, pediatrician,
        occupational_therapist, physical_therapist, pharmacist, nutritionist, oncologist, others;
    }

    public static enum QACategory {
        cosmeticsurgery, plasticsurgery, gynecology, obstetrics, dermatology, pediatrics, oncology_consultation, others;
    }
}
