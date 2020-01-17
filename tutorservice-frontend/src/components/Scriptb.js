import axios from "axios";

var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "home",
  data() {
    return {
      state: "login",
      firstName: "",
      lastName: "",
      password: "",
      rate: "",
      course: "",
      courseName: "",
      courseId: '',
      courses: [],
      tutorCourses: [],
      students: [],
      student: "",
      credentials: "",
      requests: [],
      status: false,
      day: "",
      startTime: "",
      endTime: "",
      info: null,
      userDto: null,
      userRoleDto: null,
      username: '',
      regPass: '',
      regU: '',
      id: '',
      ECSE321rate: '',  
    };
  },
  methods: {
    user: function (name, IDNumber, password, userRoleDto){
      this.name = name,
      this.ID = IDNumber,
      this.password = password,
      this.userRoleDto = userRoleDto
    },
    userRole: function (IDNumber, userID){
      this.IDNumber = IDNumber,
      this.userID = userID
    },
    getCourses: function() {
      axios.get('http://localhost:8080/Courses')     
      .then(response => {
        console.log(response)
          this.courses = response.data
        console.log(this.courses)})
      .catch(error => console.log(error));
    },
    getTutorCourses: function() {
      axios.get('http://localhost:8080/Courses/-131600492')
      .then(response => {
        console.log(response)
          this.tutorCourses = response.data
        console.log(this.tutorCourses)})
      .catch(error => console.log(error));
    },
    login: function(){
      axios.get('http://localhost:8080/login?username=\'' + this.username + '\'&password=\'' + this.password + '\'')
      .then(response => {
        if(response.data.idNumber != null){
          this.state = "menu"
          this.id=response.data.idNumber
          console.log(response)
        }
      })
      .catch((error) => {console.log(error) , this.state = "login" , alert("Username or password is incorrect, please try again.")})
    },
    register: function(){
      axios.post('http://localhost:8080/signup?name=\'' + this.regU + '\'&password=\'' + this.regPass + '\'&Type=Tutor') 
      .then(response => {
        console.log(response)
      this.state = "menu"
      this.username=this.regU
      this.password=this.regPass})
      .catch(error => console.log(error));
    },
    newRate: function() {
      axios.post(`http://localhost:8080/createRates?rate=` + this.rate + '&tutorId=-131600492&courseId=' + this.courseId)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    getRate: function(userId, ) {

    },
    menu: function() {
      this.state = "menu";
    },
    courseMenu: function() {
      this.state = "courses";
    },
    availabilities: function() {
      this.state = "availabilities";
    },
    requestMenu: function() {
      this.state = "requests";
    },
    reviews: function() {
      this.state = "reviews";
    },
    createUser: function(fN, lN, pass) {
      this.firstName = fN;
      this.lastName = lN;
      this.password = pass;
      AXIOS.post(`/users/` + firstName, lastName, password)
        .then(response => console.log(response))
        .catch(error => console.log(error));
      this.state = "menu";
    },

    getAllCourses: function() {
      AXIOS.get(`/courses`)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    applyForCourse: function(tutor, course, credentials) {
      this.tutor = tutor;
      this.course = course;
      this.credentials = credentials;
      AXIOS.post(`/courses/` + tutor, course, credentials)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    getAvailabilities: function(tutor) {
      this.tutor = tutor;
      AXIOS.get(`/availabilities/` + tutor)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    setAvailability: function(tutor, day, startTime, endTime) {
      this.tutor = tutor;
      this.day = day;
      this.startTime = startTime;
      this.endTime = endTime;
      AXIOS.post(`/availabilities/` + tutor, day, startTime, endTime)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    getRequests: function(tutor) {
      this.tutor = tutor;
      AXIOS.get(`/requests/` + tutor)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    requestAnswer: function(request, status) {
      this.request = request;
      this.status = status;
      AXIOS.post(`/requests/` + request, status)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    getAllStudents: function() {
      AXIOS.get(`/students/`)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    },
    giveReview: function(student, rating, comment, tutor) {
      this.tutor = tutor;
      this.rating = rating;
      this.comment = comment;
      this.student = student;
      AXIOS.post(`/students/` + student, rating, comment, tutor)
        .then(response => console.log(response))
        .catch(error => console.log(error));
    }
  }
};
