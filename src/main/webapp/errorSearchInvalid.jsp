<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject" %>

<!DOCTYPE html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Karla:wght@200;300;400;500&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://use.fontawesome.com/releases/v5.7.2/css/all.css"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<style>
body {
	background-color: #fff;
	font-family: 'Karla', sans-serif;
}

h1>a {
	text-decoration: none;
	color: #fff !important;
}

.intro-section {
	background-image: url(https://wallpapercave.com/wp/wp4489084.jpg);
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
	padding: 75px 95px;
	min-height: 100vh;
	display: -webkit-box;
	display: flex;
	-webkit-box-orient: vertical;
	-webkit-box-direction: normal;
	flex-direction: column;
	color: #ffffff;
}

@media ( max-width : 991px) {
	.intro-section {
		padding-left: 50px;
		padding-rigth: 50px;
	}
}

@media ( max-width : 767px) {
	.intro-section {
		padding: 28px;
	}
}

@media ( max-width : 575px) {
	.intro-section {
		min-height: auto;
	}
}

.brand-wrapper .logo {
	height: 35px;
}

@media ( max-width : 767px) {
	.brand-wrapper {
		margin-bottom: 35px;
	}
}

.intro-content-wrapper {
	width: 410px;
	max-width: 100%;
	margin-top: auto;
	margin-bottom: auto;
}

.intro-content-wrapper .intro-title {
	font-size: 40px;
	font-weight: bold;
	margin-bottom: 17px;
}

.intro-content-wrapper .intro-text {
	font-size: 19px;
	line-height: 1.37;
}

.intro-content-wrapper .btn-read-more {
	background-color: #fff;
	padding: 13px 30px;
	border-radius: 0;
	font-size: 16px;
	font-weight: bold;
	color: #000;
}

.intro-content-wrapper .btn-read-more:hover {
	background-color: transparent;
	border: 1px solid #fff;
	color: #fff;
}

@media ( max-width : 767px) {
	.intro-section-footer {
		margin-top: 35px;
	}
}

.intro-section-footer .footer-nav a {
	font-size: 20px;
	font-weight: bold;
	color: inherit;
}

@media ( max-width : 767px) {
	.intro-section-footer .footer-nav a {
		font-size: 14px;
	}
}

.intro-section-footer .footer-nav a+a {
	margin-left: 30px;
}

.form-section {
	display: -webkit-box;
	display: flex;
	-webkit-box-align: center;
	align-items: center;
	-webkit-box-pack: center;
	justify-content: center;
}

@media ( max-width : 767px) {
	.form-section {
		padding: 35px;
	}
}

.login-wrapper {
	width: 300px;
	max-width: 100%;
}

@media ( max-width : 575px) {
	.login-wrapper {
		width: 100%;
	}
}

.login-wrapper .form-control {
	border: 0;
	border-bottom: 1px solid #e7e7e7;
	border-radius: 0;
	font-size: 14px;
	font-weight: bold;
	padding: 15px 10px;
	margin-bottom: 7px;
}

.login-wrapper .form-control::-webkit-input-placeholder {
	color: #b0adad;
}

.login-wrapper .form-control::-moz-placeholder {
	color: #b0adad;
}

.login-wrapper .form-control:-ms-input-placeholder {
	color: #b0adad;
}

.login-wrapper .form-control::-ms-input-placeholder {
	color: #b0adad;
}

.login-wrapper .form-control::placeholder {
	color: #b0adad;
}

.login-title {
	font-size: 30px;
	font-weight: bold;
	margin-bottom: 30px;
}

.login-btn {
	padding: 13px 30px;
	background-color: #000;
	border-radius: 0;
	font-size: 20px;
	font-weight: bold;
	color: #fff;
}

.login-btn:hover {
	border: 1px solid #000;
	background-color: transparent;
	color: #000;
}

.forgot-password-link {
	font-size: 14px;
	color: #080808;
	text-decoration: underline;
}
.login-wrapper-footer-text {
	font-size: 14px;
	text-align: center;
}
</style>
</head>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Karla:wght@200;300;400;500&display=swap"
	rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.0.1"></script>
	
</head>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6 col-md-7 intro-section">
			<div class="brand-wrapper">
					<a href="https://stackfindover.com/">
					<img style="height:175px;width:175px;" alt="" src="./logo.png">
					</a>
			</div>
			<div class="intro-content-wrapper">
				<h1 class="intro-title">Welcome to SkyInsight !</h1>
				<p class="intro-text">Your go-to for accurate and user-friendly weather forecasts. Illuminate your day with real-time updates and personalized insights, ensuring you're always weather-ready.</p>
			</div>
		</div>
		<div class="col-sm-6 col-md-5 form-section">
			<div class="login-wrapper">
				<h2 class="login-title">Invalid location entered, Please enter a valid location</h2>

<%--				<p class="login-wrapper-footer-text">--%>
<%--					 <a href="index.jsp" class="text-reset">Back to Homepage</a>--%>
<%--				</p>--%>
			</div>
		</div>
	</div>
</div>