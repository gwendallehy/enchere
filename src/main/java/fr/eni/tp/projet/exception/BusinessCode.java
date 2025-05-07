package fr.eni.tp.projet.exception;

public class BusinessCode {
        // --- USER ---
        public static final String VALID_USER_PSEUDO_BLANK = "Le pseudo n'est pas rempli";
        public static final String VALID_USER_NAME_BLANK = "validation.user.name.blank";
        public static final String VALID_USER_FIRSTNAME_BLANK = "validation.user.firstname.blank";
        public static final String VALID_USER_EMAIL_BLANK = "validation.user.email.blank";
        public static final String VALID_USER_EMAIL_INVALID = "validation.user.email.invalid";
        public static final String VALID_USER_PHONE_INVALID = "Le numéro de téléphone n'est pas valide";
        public static final String VALID_USER_STREET_BLANK = "validation.user.street.blank";
        public static final String VALID_USER_CITY_BLANK = "validation.user.city.blank";
        public static final String VALID_USER_POSTALCODE_INVALID = "Le code postal n'est pas valide";
        public static final String VALID_USER_PASSWORD_BLANK = "validation.user.password.blank";
        public static final String VALID_USER_PASSWORD_WEAK = "validation.user.password.weak";
        public static final String VALID_USER_PASSWORD_CONFIRM_PASSWORD = "Les mots de passes sont différents";

        // --- ARTICLE ---
        public static final String VALID_ARTICLE_NAME_BLANK = "validation.article.name.blank";
        public static final String VALID_ARTICLE_DESCRIPTION_BLANK = "validation.article.description.blank";
        public static final String VALID_ARTICLE_STARTDATE_IS_AFTER_ENDDATE = "La date de début doit être avant la date de fin";
        public static final String VALID_ARTICLE_STARTDATE_INVALID = "La date de début est invalide";
        public static final String VALID_ARTICLE_ENDDATE_INVALID = "validation.article.enddate.invalid";
        public static final String VALID_ARTICLE_BETPRICE_INVALID = "validation.article.betprice.invalid";
        public static final String VALID_ARTICLE_SALEPRICE_INVALID = "validation.article.saleprice.invalid";
        public static final String VALID_ARTICLE_STATUS_INVALID = "validation.article.status.invalid";
        public static final String VALID_ARTICLE_PICTURE_INVALID = "validation.article.picture.invalid";
        public static final String VALID_ARTICLE_USERID_INVALID = "validation.article.userid.invalid";

        // --- PICKUP ---
        public static final String VALID_PICKUP_STREET_BLANK = "validation.pickup.street.blank";
        public static final String VALID_PICKUP_CITY_BLANK = "validation.pickup.city.blank";
        public static final String VALID_PICKUP_POSTALCODE_INVALID = "validation.pickup.postalcode.invalid";

        // --- CATEGORY ---
        public static final String VALID_CATEGORY_WORDING_BLANK = "validation.category.wording.blank";
    }

