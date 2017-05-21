package com.joshua.zhangrui.global;


/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2016
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2016/11/1 21:56
 * <p>
 * 描 述 ：服务器地址链接
 * <p>
 * ============================================================
 **/

public class Server {
    //服务器地址
//    public final static String SERVER = "http://192.168.0.18:8080/ZR/MainServlet";
    public final static String SERVER = "http://114.55.148.222:8080/ZR/MainServlet";

    public static final String SERVER_REGISTER ="register";
    public static final String SERVER_LOGIN = "login";

    public static final String SERVER_ADD_COURSE = "addCourse";
    public static final String SERVER_QUERY_COURSE = "queryAllCourse";
    public static final String SERVER_QUERY_COURSE_STU = "queryCourse";
    public static final String SERVER_JOIN_COURSE = "joinCourse";
    public static final String SERVER_QUERY_JOIN_STUDENT = "queryJoinStudent";
    public static final String SERVER_ADD_STUDENT = "confirmJoin";

    public static final String SERVER_QUERY_SUBJECT = "queryAllQuestion";
    public static final String SERVER_UPDATE_QUESTION = "updateCourseQuestion";
    public static final String SERVER_ADD_COURSE_QUESTION = "addCourseQuestion";
    public static final String SERVER_QUERY_CHOOSE_COURSE = "queryChooseCourse";

    public static final String SERVER_QUERY_COURSE_SUBJECT = "allSubject";
    public static final String SERVER_TEST_LIST = "testList";
    public static final String SERVER_ADD_EXAM_SCORE = "addExamScore";
    public static final String SERVER_QUERY_EXAM_SCORE_STU = "queryExamScore_Student";

    public static final String SERVER_QUERY_EXAM_SCORE_TEA = "queryExamScore_Teacher";
    public static final String SERVER_ASK_QUESTION = "askQuestion";
    public static final String SERVER_ALL_QUESTION_STU = "allQuestion_Student";
    public static final String SERVER_ALL_QUESTION_TEA = "allQuestion_Teacher";
    public static final String SERVER_ANSWER_QUESTION = "answerQuestion";

    public static final String SERVER_UPDATE_STUDENT_INFO = "updateUserInfo";
    public static final String SERVER_CHANGE_PWD = "updateUserPsw";

    public static final String SERVER_DELETE_COURSE = "deleteCourse";
    public static final String SERVER_DELETE_QUESTION = "deleteQuestion";

    public static final String SERVER_UPDATE_TEACHER_INFO = "updateTeacherInfo";
    public static final String SERVER_DELETE_COURSE_TEACHER = "deleteCourseTeacher";
    public static final String SERVER_DELETE_STUDENT_TEACHER = "deleteStudentTeacher";
    public static final String SERVER_DELETE_GRADE_TEACHER = "deleteGradeTeacher";

    //Admin
    public static final String QUERY_ALL_STUDENT = "queryAllStudent";
    public static final String SERVER_DELETE_STUDENT_ADMIN = "deleteStudent";
    public static final String SERVER_GET_STUDENT_INFO= "queryStudentInfo";


    public static final String QUERY_ALL_TEACHER = "queryAllTeacher";
    public static final String SERVER_DELETE_TEACHER_ADMIN = "deleteTeacher";
    public static final String SERVER_GET_TEACHER_INFO= "queryTeacherInfo";

    public static final String SERVER_QUERY_GRADE_ADMIN= "queryStudentScore";
    public static final String SERVER_DELETE_GRADE_ADMIN= "deleteGradeAdmin";
    public static final String SERVER_QUERY_SUBJECT_ADMIN= "queryStore";
    public static final String SERVER_DELETE_SUBJECT_ADMIN= "deleteStoreByName";




}
