package org.smartregister.chw.tbleprosy.util;

public interface Constants {

    int REQUEST_CODE_GET_JSON = 2244;
    String ENCOUNTER_TYPE = "encounter_type";
    String STEP_ONE = "step1";
    String STEP_TWO = "step2";
    String SKELETON_VISIT_GROUP = "skeleton_visit_group";


    interface JSON_FORM_EXTRA {
        String JSON = "json";
        String ENCOUNTER_TYPE = "encounter_type";
        String EVENT_TYPE = "eventType";
    }

    interface EVENT_TYPE {
        String SKELETON_ENROLLMENT = "Skeleton Enrollment";
        String SKELETON_SERVICES = "Skeleton Services";
        String SKELETON_FOLLOW_UP_VISIT = "Skeleton Follow-up Visit";
        String VOID_EVENT = "Void Event";
        String CLOSE_SKELETON_SERVICE = "Close Skeleton Service";

    }

    interface FORMS {
        String SKELETON_REGISTRATION = "skeleton_enrollment";
        String SKELETON_FOLLOW_UP_VISIT = "skeleton_followup_visit";
    }

    interface SKELETON_FOLLOWUP_FORMS {
        String MEDICAL_HISTORY = "skeleton_service_medical_history";
        String PHYSICAL_EXAMINATION = "skeleton_service_physical_examination";
        String HTS = "skeleton_service_hts";
    }

    interface TABLES {
        String SKELETON_ENROLLMENT = "ec_skeleton_enrollment";
        String SKELETON_SERVICE = "ec_skeleton_services";
    }

    interface ACTIVITY_PAYLOAD {
        String BASE_ENTITY_ID = "BASE_ENTITY_ID";
        String FAMILY_BASE_ENTITY_ID = "FAMILY_BASE_ENTITY_ID";
        String SKELETON_FORM_NAME = "SKELETON_FORM_NAME";
        String MEMBER_PROFILE_OBJECT = "MemberObject";
        String EDIT_MODE = "editMode";
        String PROFILE_TYPE = "profile_type";

    }

    interface ACTIVITY_PAYLOAD_TYPE {
        String REGISTRATION = "REGISTRATION";
        String FOLLOW_UP_VISIT = "FOLLOW_UP_VISIT";
    }

    interface CONFIGURATION {
        String SKELETON_ENROLLMENT = "skeleton_enrollment";
    }

    interface SKELETON_MEMBER_OBJECT {
        String MEMBER_OBJECT = "memberObject";
    }

    interface PROFILE_TYPES {
        String SKELETON_PROFILE = "skeleton_profile";
    }

    interface VALUES {
        String NONE = "none";
        String CHORDAE = "chordae";
        String HIV = "hiv";
        String RBG = "random_blood_glucose_test";
        String FBG = "fast_blood_glucose_test";
        String HYPERTENSION = "hypertension";
        String SILICON_OR_LEXAN = "silicon_or_lexan";
        String NEGATIVE = "negative";
        String SATISFACTORY = "satisfactory";
        String NEEDS_FOLLOWUP = "needs_followup";
        String YES = "yes";
    }

    interface TABLE_COLUMN {
        String GENITAL_EXAMINATION = "genital_examination";
        String SYSTOLIC = "systolic";
        String DIASTOLIC = "diastolic";
        String ANY_COMPLAINTS = "any_complaints";
        String CLIENT_DIAGNONISED_WITH = "is_client_diagnosed_with_any";
        String COMPLICATION_TYPE = "type_complication";
        String HEMATOLOGICAL_DISEASE_SYMPTOMS = "any_hematological_disease_symptoms";
        String KNOWN_ALLEGIES = "known_allergies";
        String HIV_RESULTS = "hiv_result";
        String HIV_VIRAL_LOAD = "hiv_viral_load_text";
        String TYPE_OF_BLOOD_FOR_GLUCOSE_TEST = "type_of_blood_for_glucose_test";
        String BLOOD_FOR_GLUCOSE = "blood_for_glucose";
        String DISCHARGE_CONDITION = "discharge_condition";
        String IS_MALE_PROCEDURE_CIRCUMCISION_CONDUCTED = "is_male_procedure_circumcision_conducted";
    }

}
