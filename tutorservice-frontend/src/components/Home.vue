<template>
<html>
  <head>
    <title>MyTutor</title>
    <!-- <link rel="stylesheet" href="style.css"> -->
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
    />
    <script type="application/javascript" src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script
      type="application/javascript"
      src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
    ></script>
    <script
      type="application/javascript"
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
    ></script>
  </head>

  <body>
    <!-- Navigation Bar -->
    <section id="nav-bar">
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">
          <img style="height:20px; width: auto" src="../assets/Logo_name.png" />
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link" href="#" @click="menu">HOME</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">CONTACT</a>
            </li>
          </ul>
        </div>
      </nav>
    </section>

    <!-- Carousel -->
    <section id="carousel" v-if="state === 'login'" style="height:650px;">
    <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
        <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
        <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
      </ol>
      <div class="carousel-inner">
        <div class="carousel-item active" style="height:650px;">
          <img src="../assets/book.jpg" class="d-block w-100">
          <div class="carousel-caption d-none d-md-block">
            <h1><b><font size="90">My Convenience.</font></b></h1>
            <h5>Get access to hundreds of courses with just a click.</h5>
            <a href="#credentials">
            <button type="submit" class="btn btn-primary">Get Started</button>
            </a>
          </div>
        </div>
        <div class="carousel-item" style="height:650px;">
          <img src="../assets/classroom.jpg" class="d-block w-100">
          <div class="carousel-caption d-none d-md-block">
            <h1><b><font size="90">My Schedule.</font></b></h1>
            <h5>Set a schedule that works best for you.</h5>
            <a href="#credentials">
            <button type="submit" class="btn btn-primary">Get Started</button>
            </a>
          </div>
        </div>
        <div class="carousel-item" style="height:650px;">
          <img src="../assets/grad.jpg" class="d-block w-100">
          <div class="carousel-caption d-none d-md-block">
            <h1><b><font size="90">My Future.</font></b></h1>
            <h5>Create the building blocks to a successful career.</h5>
            <a href="#credentials">
            <button type="submit" class="btn btn-primary">Get Started</button>
            </a>
          </div>
        </div>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
    </section>

    <!-- Login/Register forms -->
    <section id="credentials" v-if="state === 'login'">
      <div class="jumbotron">
        <div class="row">
          <div class="col-md">
            <h1 class="display-4">Log In</h1>
            <!-- Login Form -->
            <form id="login">
              <div class="form-group">
                <label for="exampleInputEmail1">Username</label>
                <input
                  type="text"
                  class="form-control"
                  id="exampleInputEmail1"
                  aria-describedby="emailHelp"
                  placeholder="Enter username"
                  v-model="username"
                />
              </div>
              <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input
                  type="password"
                  class="form-control"
                  id="exampleInputPassword1"
                  placeholder="Enter password"
                  v-model="password"
                />
              </div>
              <button type="button" class="btn btn-primary" @click="login()">Submit</button>
            </form>
          </div> 

          <div class="line"></div>

          <!-- Register Form -->
          <div class="row">
            <div class="col-md">
              <h1 class="display-4">Register</h1>
              <!-- Login Form -->
              <form id="register">
                <div class="form-group">
                  <label for="exampleInputEmail2">Username</label>
                  <input
                    type="text"
                    class="form-control"
                    id="exampleInputEmail2"
                    aria-describedby="emailHelp"
                    placeholder="Enter username"
                    v-model="regU"
                  />
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword2">Password</label>
                  <input
                    type="password" 
                    class="form-control"
                    id="exampleInputPassword2"
                    placeholder="Enter password"
                    v-model="regPass"
                  />
                </div>
                <button type="submit" class="btn btn-primary" @click="register()">Submit</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!--- Tutor Menu ---->
    <section id="tutor-menu" v-if="state === 'menu'">
      <div class="container">
        <h1 style="text-align: center;" class="display-4">My Profile</h1>
        <div class="row">
          <div class="col-md">
            <div class="media">
              <img id="profilePicture" src="../assets/profile.jpg" class="mr-3" />
            </div>
          </div>
          <div class="menu-buttons" style="align-self: center;">
            <button type="submit" class="btn btn-dark btn-lg" @click="courseMenu(); getCourses(); getTutorCourses()">Courses</button>
            <button type="submit" class="btn btn-dark btn-lg" @click="availabilities">Availabilities</button>
            <button type="submit" class="btn btn-dark btn-lg" @click="requestMenu">Session requests</button>
            <button type="submit" class="btn btn-dark btn-lg" @click="reviews">Student reviews</button>
          </div>
        </div>
      </div>
    </section>

    <!--- Availabilities ---->
    <section id="availabilities" v-if="state === 'availabilities'">
      <div class="container">
        <h1 style="text-align: center;" class="display-4">My Availabilities</h1>
        <div class="row">
          <div class="col-md"></div>
          <div class="col-md">
            <h4 style="color:red">Start time (XX:XXAM/PM)</h4>
          </div>
          <div class="col-md">
            <h4 style="color:red">End time (XX:XXAM/PM)</h4>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Monday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'MONDAY', startMonday, endMonday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startMonday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                class="form-control"
                id="endMonday"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Tuesday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'TUESDAY', startTuesday, endTuesday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startTuesday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                id="endTuesday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Wednesday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'WEDNESDAY', startWednesday, endWednesday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startWednesday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                id="endWednesday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Thursday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'THURSDAY', startThursday, endThursday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startThursday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                id="endThursday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Friday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'FRIDAY', startFriday, endFriday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startFriday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                id="endFriday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Saturday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'SATURDAY', startSaturday, endSaturday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startSaturday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                id="endSaturday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md">
            <h3 id="day">Sunday</h3>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="button-addon1"
                  @click="setAvailability(tutor, 'SUNDAY', startSunday, endSunday)"
                >Submit</button>
              </div>
              <input
                type="text"
                id="startSunday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
          <div class="col-md">
            <div class="input-group mb-3">
              <input
                type="text"
                id="endSunday"
                class="form-control"
                placeholder
                aria-label="Example text with button addon"
                aria-describedby="button-addon1"
              />
            </div>
          </div>
        </div>
      </div>
    </section>

    <!--- Student Reviews ---->
    <section id="reviews" v-if="state === 'reviews'">
      <div class="contaier" style="text-align: center;">
        <h1 style="text-align: center;" class="display-4">Reviews</h1>
        <br>
          <!-- Search form -->
          <input
            class="form-control"
            type="text"
            placeholder="Search"
            aria-label="Search"
            style="width:500px; text-align:center; margin-left: auto; margin-right: auto;"
          />
        <br />
        <div class="row aligh-items-center">
          <div class="col-md">
            <table class="table table-light" style="width: 750px;  margin-left: auto; margin-right: auto">
              <thead class="thead-light">
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Student ID</th>
                  <th scope="col">Student Name</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th scope="row">1</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">2</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">3</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">4</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">5</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">6</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">7</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
                <tr>
                  <th scope="row">8</th>
                  <td>260868693</td>
                  <td>John Doe</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="row align-items-center">
          <div style="width:100%; height: 100px;">
            <select>
              <option value="0">Select student:</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
            </select>
          </div>
        </div>
        <div style="width:100%; height: 100px;">
          <select>
            <option value="0">Select rating:</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select>
        </div>
        <div class="form-group; col-md">
          <label for="comment">Comment:</label>
          <textarea class="form-control" rows="4" id="comment" style="width: 1000px;  margin-left: auto; margin-right: auto"></textarea>
        </div>
        <br />
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
      <br />
      <br />
      <br />
    </section>

    <!--- Session requests ---->
    <section id="requests" v-if="state === 'requests'">
      <div class="container">
        <h1 style="text-align: center;" class="display-4">Requests</h1>
        <div class="col-md">
          <table class="table table-light">
            <thead class="thead-light">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Student ID</th>
                <th scope="col">Student Name</th>
                <th scope="col">Time</th>
                <th scope="col">Class</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">3</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">4</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">5</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">6</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">7</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
              <tr>
                <th scope="row">8</th>
                <td>260868693</td>
                <td>John Doe</td>
                <td>12:00-13:00</td>
                <td>ECSE321</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div style="text-align:center">
          <div style="width:100%; height: 100px;">
            <select>
              <option value="0">Select request:</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
            </select>
          </div>
        </div>
      </div>
      <div style="text-align:center">
        <button
          type="submit"
          class="btn btn-primary ; col-md"
          style="width:15%;text-align:center"
        >Accept Request</button>
        <button
          type="submit"
          class="btn btn-primary ; col-md"
          style="width:15%;text-align:center"
        >Decline Request</button>
      </div>
    </section>

    <!--- Courses ---->
    <section id="courses" v-if="state === 'courses'">
      <div class="container">
        <h1 style="text-align: center;" class="display-4">Courses</h1>
        <div class="row">
          <div class="col-md">
            <div class="row align">
              <h3>My current courses</h3>
            </div>
            <div class="row">
              <div class="col-md">
                <table class="table table-light">
                  <thead class="thead-light">
                    <tr>
                      <th scope="col">Course ID</th>
                      <th scope="col">Course Name</th>
                      <th scope="col">Rate</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="tutorCourse in tutorCourses">
                      <td> {{ tutorCourse.courseId }} </td>
                      <td> {{ tutorCourse.name }} </td>
                      <td> {{ ECSE321rate }} </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="col-md">
                <div style="width:200px; height: 100px;">
                  <p>Enter course ID:</p>
                    <input
                      type="text"
                      class="form-control"
                      placeholder
                      aria-label="Example text with button addon"
                      aria-describedby="button-addon1"
                      v-model="courseId"/>
                </div>
                <div class="rate">
                  <p>Enter a new rate:</p>
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <button
                        type="reset"
                        class="btn btn-outline-secondary"
                        id="button-addon1"
                        value = "Refresh Page"
                      @click="newRate">Submit</button>
                    </div>
                    <input
                      type="text"
                      class="form-control"
                      placeholder
                      aria-label="Example text with button addon"
                      aria-describedby="button-addon1"
                      v-model="rate"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <h3>Available courses</h3>
            </div>
            <div class="row" style="top:100px; height:300px;">
              <div class="col-md">
                <table class="table table-light">
                  <thead class="thead-light">
                    <tr>
                      <th scope="col">Course ID</th>
                      <th scope="col">Course Name</th>
                      <th scope="col">Rate</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="course in courses">
                      <td> {{ course.courseId }} </td>
                      <td> {{ course.name }} </td>
                      <td> {{ course.averageRate }} </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="col-md">
                <div style="width:200px; height: 50px;">
                  <p>Enter course ID:</p>
                    <input
                      type="text"
                      class="form-control"
                      placeholder
                      aria-label="Example text with button addon"
                      aria-describedby="button-addon1"
                      v-model="courseId"/>
                </div>
                                      <br><br>
                <label for="comment">Qualifications:</label>
                <textarea style="width:300px;" class="form-control" rows="1" id="qualification" placeholder="University"></textarea>
                <br>
                <textarea style="width:300px;" class="form-control" rows="1" id="qualification" placeholder="Major"></textarea>
                <br>
                <textarea style="width:300px;" class="form-control" rows="1" id="qualification" placeholder="Start Year"></textarea>
                <br>
                <textarea style="width:300px;" class="form-control" rows="1" id="qualification" placeholder="End Year"></textarea>
                <br>
                <div style="width:200px; height: 50px;">
                  <select>
                    <option value="0">Ongoing?</option>
                    <option value="Yes">Yes</option>
                    <option value="No">No</option>
                  </select>
                </div>
                <textarea class="form-control" rows="3" id="qualification" placeholder="Summary of past experience."></textarea>
                <br>
                <button type="submit" class="btn btn-primary">Apply Now</button>
                <br><br><br><br><br>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section id="footer">
      <div class="footer">
        <div class="footer-copyright text-center py-3">
          © 2019 Copyright:
          <a href="insertLink">MyTutor.com</a>
        </div>
      </div>
    </section>
  </body>
</html>
</template>

<script src="./Scriptb.js">

</script>

<style src="./Styleb.css">

</style>
