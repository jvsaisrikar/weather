<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.json.JSONArray"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Weather App</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- Custom CSS -->
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
}

.weather-icon {
	display: block;
	margin: 0 auto;
}

.weather-container {
	max-width: 600px;
	margin: 0 auto;
	background-color: #fff;
	border-radius: 10px;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.weather-heading {
	text-align: center;
	margin-bottom: 20px;
	font-size: 24px;
	color: #333;
}

.weather-info {
	padding: 10px 0;
	border-bottom: 1px solid #ccc;
}

.weather-info:last-child {
	border-bottom: none;
}

.weather-info label {
	font-weight: bold;
	width: 150px;
	display: inline-block;
	color: #555;
}

.weather-info span {
	color: #333;
}

.navbar-brand img {
	max-height: 75px; /* Increased logo size */
}

.navbar-toggler-icon {
	background-color: #007bff; /* Blue color for toggler icon */
}

.left-half, .middle, .right-half {
	padding: 20px;
	height: 85vh;
}

.details-box {
	background-color: rgba(255, 255, 255, 0.8); /* Transparent background */
	border-radius: 10px;
	padding: 30px;
	/* margin:-10px; */
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	text-align: center;
}

.night-background {
	/* background-image: url('mr.gif'); */
	/* Replace 'night.jpg' with the path to your night background image */
	background-size: cover;
	max-height: 83vh;
	background-position: center;
	border-radius: 25px;
}

.row {
	padding: 15px
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg nav-fill w-100 navbar-dark "
		style="background-color: black">
		<div class="container-fluid">
			<!-- Brand -->
			<a class="navbar-brand" href="#"><img src="logo.png" alt="Logo"></a>
			<!-- Brand name in the center -->
			<div class="navbar-text mx-auto">
				<span class="navbar-brand mb-0 h1">SkyInsight </span>
			</div>
			<!-- Toggler button -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- Navbar links -->
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ml-auto">

					<form action="SearchServlet" method="post" style="margin:10px">
						<!-- Hidden input field with value "current" -->
						<input type="hidden" name="locationValue" value="current">
						<!-- Button to submit the form -->
						<input style="padding: 0px;margin: 0px;background: transparent;border: 0;color: white;" type="submit" value="Current Location">
					</form>
					<form action="SearchServlet" method="post"
						class="form-inline my-2 my-lg-0" style="margin-right: 20px">
						
						<input required class="form-control mr-sm-2" type="text"
							name="locationValue" placeholder="Enter Zip / City-State"
							aria-label="Search">
						<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
					</form>

					<li class="nav-item"><a class="btn " style="color: white"
						href="SignoutServlet" data-toggle="modal"
						data-target="#confirmationModal">Sign Out</a></li>
					<div class="modal fade" id="confirmationModal" tabindex="-1"
						role="dialog" aria-labelledby="confirmationModalLabel"
						aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="confirmationModalLabel">Confirmation</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									Are you sure you want to logout from
									<%=session.getAttribute("username")%>
									?
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Cancel</button>

									<a class="btn btn-success" href="SignoutServlet">Confirm</a>
								</div>
							</div>
						</div>
					</div>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Left Column -->
	<div class="container-fluid">
		<%
		Object userDataObj = request.getAttribute("userData");
		if (userDataObj instanceof JSONObject) {
			JSONObject userData = (JSONObject) userDataObj;
			JSONObject weatherData = userData.optJSONObject("weather");
			JSONObject weatherForcastData = userData.optJSONObject("weatherForecast");
			if (weatherData != null && weatherForcastData != null) {
				JSONObject location = weatherData.optJSONObject("location");
				JSONObject current = weatherData.optJSONObject("current");
				JSONObject alerts = weatherData.optJSONObject("alerts");
				JSONObject condition = current.optJSONObject("condition");
				JSONObject forcastData = weatherForcastData.optJSONObject("forecast");
				JSONArray forecastdayArray = forcastData.optJSONArray("forecastday");
				if (location != null && current != null) {

			String time = location.optString("localtime").substring(11);
			String hour = time.substring(0, 2);
			String image = "";
			if (hour.endsWith(":")) {
				hour = "0" + hour;
				hour = hour.substring(0, 2);
			}

			int timeInInt = Integer.parseInt(hour);
			// Determine the time of day

			if (timeInInt >= 6 && timeInInt < 18) {
				image = "mr.gif";
			} else {
				image = "giphy (1).gif";
			}
		%>

		<div class="row">
			<div
				class="col-md-2 left-half night-background d-flex align-items-center justify-content-center"
				style="background-image: url('<%=image%>')">
				<!-- Location box -->
				<div class="details-box">
					<p>
						Hey 
						<a href="#"><%=session.getAttribute("username")%></a>
					</p>
					<p>
					<p>
						Location:
						<%=location.optString("name")%></p>
					<p>
						<%=location.optString("localtime")%></p>
					<p>
						Temperature:
						<%=current.optString("temp_f")%>°F
					</p>

				</div>
			</div>

			<!-- Right Column -->
			<!-- Right Column -->

			<div class="col-md-5 middle">
				<h5>Hourly Weather Forecast</h5>
				<div id="hourlyCollapse"
					style="width: 570px; height: 300px; margin-top: -25px"
					aria-labelledby="hourlyHeading">
					<div class="card-body">
						<canvas id="hourlyChart" width="600" height="300"></canvas>

						<%
						JSONArray hourlyData = forecastdayArray.optJSONObject(0).optJSONArray("hour");
						String jsonString = hourlyData.toString();
						%>

						<script>
                // Parse JSON data
                var hourlyWeatherData =  JSON.parse('<%=jsonString%>');
                
                // Extract time labels and temperature values from hourlyWeatherData
                var timeLabels = []; // Array to store time labels
                var temperatureValues = []; // Array to store temperature values
                
                // Iterate over hourlyWeatherData to populate timeLabels and temperatureValues
                <%for (int i = 0; i < forecastdayArray.optJSONObject(0).optJSONArray("hour").length(); i++) {%>
				temperatureValues.push( <%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%>);
				timeLabels.push( <%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getString("time").substring(11, 13)%>);
				console.log(temperatureValues)	;
				console.log(timeLabels)	;
				<%}%>
                
                // Render bar graph
                var ctx = document.getElementById('hourlyChart').getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: timeLabels,
                        datasets: [{
                            label: 'Temperature (°F)',
                            data: temperatureValues,
                            backgroundColor: 'rgba(255, 99, 132, 0.2)', // Customize bar color
                            borderColor: 'rgba(255, 99, 132, 1)', // Customize border color
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: 
                        {   
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }],
                           
                        }
                    },
                   
                });
            </script>
					</div>
				</div>
				<h5>Hourly Cloud Percentage</h5>
				<div id="hourlyCloudCollapse"
					style="width: 570px; height: 300px; margin-top: -25px"
					aria-labelledby="hourlyCHeading">
					<div class="card-body">
						<canvas id="hourlyCloudChart" width="600" height="300"></canvas>

						<%
						JSONArray hourlyCloudData = forecastdayArray.optJSONObject(0).optJSONArray("hour");
						String jsonCloudString = hourlyCloudData.toString();
						%>

						<script>
                // Parse JSON data
                var hourlyCloudWeatherData =  JSON.parse('<%=jsonCloudString%>');

							// Extract time labels and temperature values from hourlyWeatherData
							var timeCloudLabels = []; // Array to store time labels
							var temperatureCloudValues = []; // Array to store temperature values

							// Iterate over hourlyWeatherData to populate timeLabels and temperatureValues
						<%for (int i = 0; i < forecastdayArray.optJSONObject(0).optJSONArray("hour").length(); i++) {%>
							temperatureCloudValues
									.push(
						<%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("cloud")%>
							);
							timeCloudLabels
									.push(
						<%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getString("time").substring(11, 13)%>
							);
						<%}%>
							// Render bar graph
							var ctxCloud = document.getElementById('hourlyCloudChart').getContext('2d');
							var myChart = new Chart(
									ctxCloud,
									{
										type : 'line',
										data : {
											labels : timeLabels,
											datasets : [ {
												label : 'Cloud Percentage',
												data : temperatureValues,
												backgroundColor : 'rgba(255, 99, 132, 0.2)', // Customize bar color
												borderColor : 'rgba(255, 99, 132, 1)', // Customize border color
												borderWidth : 1
											} ]
										},
										options : {
											scales : {
												yAxes : [ {
													ticks : {
														beginAtZero : true
													}
												} ]
											}
										}
									});
						</script>
					</div>
				</div>
			</div>
			<div class="col-md-5 right-half"
				style="display: flex; flex-direction: column; justify-content: space-between">
				<div>
					<h5>Hourly Weather Forecast for the following week</h5>
					<div id="accordion" style="width: 570px">
						<!-- Option 1: Today -->
						<div class="card">
							<div class="card-header" id="headingOne">
								<h5 class="mb-0">
									<button class="btn btn-black" data-toggle="collapse"
										data-target="#collapseOne" aria-expanded="true"
										aria-controls="collapseOne">
										
										<%=forecastdayArray.optJSONObject(0).optString("date")%>: Today
									</button>
								</h5>
							</div>

							<div id="collapseOne" class="collapse"
								aria-labelledby="headingOne" data-parent="#accordion">
								<div class="card-body"
									style="max-height: 350px; overflow-y: auto;">

									<table
										class="table table-dark table-bordered table-striped table-hover">
										<thead>
											<tr>
												<th scope="col">Time</th>
												<th scope="col">Condition</th>
												<th scope="col">Temperature (°F)</th>
												<th scope="col">Wind Speed Mph</th>
												<th scope="col">Humidity</th>
												<th scope="col">Atmospheric Pressure (in)</th>
												<th scope="col">Precipitation (mm)</th>

											</tr>
										</thead>
										<tbody>
											<%
											for (int i = 0; i < forecastdayArray.optJSONObject(0).optJSONArray("hour").length(); i++) {
											%>
											<tr>
												<td><%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
												<td><img style="height: 30px; width: 30px"
													src=<%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
												<td><%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
												<td><%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>
												<td><%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>

												<td><%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
												<td><%=forecastdayArray.optJSONObject(0).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>

											</tr>
											<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header" id="headingTwo">
								<h5 class="mb-0">
									<button class="btn btn-black" data-toggle="collapse"
										data-target="#collapseTwo" aria-expanded="true"
										aria-controls="#collapseTwo">
										<%=forecastdayArray.optJSONObject(1).optString("date")%>
									</button>
								</h5>
							</div>

							<div id="collapseTwo" class="collapse"
								aria-labelledby="headingTwo" data-parent="#accordion">
								<div class="card-body"
									style="max-height: 350px; overflow-y: auto;">

									<table
										class="table table-dark table-bordered table-striped table-hover">
										<thead>
											<tr>
												<th scope="col">Time</th>
												<th scope="col">Condition</th>
												<th scope="col">Temperature (°F)</th>
												<th scope="col">Wind Speed Mph</th>
												<th scope="col">Humidity</th>
												<th scope="col">Atmospheric Pressure (in)</th>
												<th scope="col">Precipitation (mm)</th>

											</tr>
										</thead>
										<tbody>
											<%
											for (int i = 0; i < forecastdayArray.optJSONObject(1).optJSONArray("hour").length(); i++) {
											%>
											<tr>
												<td><%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
												<td><img style="height: 30px; width: 30px"
													src=<%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
												<td><%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
												<td><%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>
												<td><%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>

												<td><%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
												<td><%=forecastdayArray.optJSONObject(1).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>

											</tr>
											<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header" id="headingThree">
								<h5 class="mb-0">
									<button class="btn btn-black" data-toggle="collapse"
										data-target="#collapseThree" aria-expanded="true"
										aria-controls="#collapseThree">
										<%=forecastdayArray.optJSONObject(2).optString("date")%>
									</button>
								</h5>
							</div>

							<div id="collapseThree" class="collapse"
								aria-labelledby="headingThree" data-parent="#accordion">
								<div class="card-body"
									style="max-height: 350px; overflow-y: auto;">

									<table
										class="table table-dark table-bordered table-striped table-hover">
										<thead>
											<tr>
												<th scope="col">Time</th>
												<th scope="col">Condition</th>
												<th scope="col">Temperature (°F)</th>
												<th scope="col">Wind Speed Mph</th>
												<th scope="col">Humidity</th>
												<th scope="col">Atmospheric Pressure (in)</th>
												<th scope="col">Precipitation (mm)</th>

											</tr>
										</thead>
										<tbody>
											<%
											for (int i = 0; i < forecastdayArray.optJSONObject(2).optJSONArray("hour").length(); i++) {
											%>
											<tr>
												<td><%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
												<td><img style="height: 30px; width: 30px"
													src=<%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
												<td><%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
												<td><%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>
												<td><%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>

												<td><%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
												<td><%=forecastdayArray.optJSONObject(2).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>

											</tr>
											<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header" id="headingFour">
								<h5 class="mb-0">
									<button class="btn btn-black" data-toggle="collapse"
										data-target="#collapseFour" aria-expanded="true"
										aria-controls="#collapseFour">
										<%=forecastdayArray.optJSONObject(3).optString("date")%>
									</button>
								</h5>
							</div>

							<div id="collapseFour" class="collapse"
								aria-labelledby="headingFour" data-parent="#accordion">
								<div class="card-body"
									style="max-height: 350px; overflow-y: auto;">

									<table
										class="table table-dark table-bordered table-striped table-hover">
										<thead>
											<tr>
												<th scope="col">Time</th>
												<th scope="col">Condition</th>
												<th scope="col">Temperature (°F)</th>
												<th scope="col">Wind Speed Mph</th>
												<th scope="col">Humidity</th>
												<th scope="col">Atmospheric Pressure (in)</th>
												<th scope="col">Precipitation (mm)</th>

											</tr>
										</thead>
										<tbody>
											<%
											for (int i = 0; i < forecastdayArray.optJSONObject(3).optJSONArray("hour").length(); i++) {
											%>
											<tr>
												<td><%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
												<td><img style="height: 30px; width: 30px"
													src=<%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
												<td><%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
												<td><%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>
												<td><%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>

												<td><%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
												<td><%=forecastdayArray.optJSONObject(3).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>

											</tr>
											<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header" id="headingFive">
								<h5 class="mb-0">
									<button class="btn btn-black" data-toggle="collapse"
										data-target="#collapseFive" aria-expanded="true"
										aria-controls="#collapseFive">
										<%=forecastdayArray.optJSONObject(4).optString("date")%>
									</button>
								</h5>
							</div>

							<div id="collapseFive" class="collapse"
								aria-labelledby="headingFive" data-parent="#accordion">
								<div class="card-body"
									style="max-height: 350px; overflow-y: auto;">

									<table
										class="table table-dark table-bordered table-striped table-hover">
										<thead>
											<tr>
												<th scope="col">Time</th>
												<th scope="col">Condition</th>
												<th scope="col">Temperature (°F)</th>
												<th scope="col">Wind Speed Mph</th>
												<th scope="col">Humidity</th>
												<th scope="col">Atmospheric Pressure (in)</th>
												<th scope="col">Precipitation (mm)</th>

											</tr>
										</thead>
										<tbody>
											<%
											for (int i = 0; i < forecastdayArray.optJSONObject(4).optJSONArray("hour").length(); i++) {
											%>
											<tr>
												<td><%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
												<td><img style="height: 30px; width: 30px"
													src=<%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
												<td><%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
												<td><%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>
												<td><%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>

												<td><%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
												<td><%=forecastdayArray.optJSONObject(4).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>

											</tr>
											<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- Option 2: Tomorrow -->
						<div class="card">
							<div class="card-header" id="headingSix">
								<h5 class="mb-0">
									<button class="btn btn-black collapsed" data-toggle="collapse"
										data-target="#collapseSix" aria-expanded="false"
										aria-controls="#collapseSix">
										<%=forecastdayArray.optJSONObject(5).optString("date")%></button>

								</h5>
							</div>
							<div id="collapseSix" class="collapse"
								aria-labelledby="headingSix" data-parent="#accordion">
								<div class="card-body"
									style="max-height: 350px; overflow-y: auto;">
									<table
										class="table table-dark table-bordered table-striped table-hover">
										<thead>
											<tr>
												<th scope="col">Time</th>
												<th scope="col">Condition</th>
												<th scope="col">Temperature (°F)</th>
												<th scope="col">Wind Speed Mph</th>
												<th scope="col">Humidity</th>
												<th scope="col">Atmospheric Pressure (in)</th>
												<th scope="col">Precipitation (mm)</th>


											</tr>
										</thead>
										<tbody>
											<%
											for (int i = 0; i < forecastdayArray.optJSONObject(5).optJSONArray("hour").length(); i++) {
											%>
											<tr>
												<td><%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
												<td><img style="height: 30px; width: 30px"
													src=<%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
												<td><%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
												<td><%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>

												<td><%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>
												<td><%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
												<td><%=forecastdayArray.optJSONObject(5).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>


											</tr>
											<%
											}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header" id="headingSeven">
								<h5 class="mb-0">
									<button class="btn btn-black collapsed" data-toggle="collapse"
											data-target="#collapseSeven" aria-expanded="false"
											aria-controls="#collapseSeven">
										<%=forecastdayArray.optJSONObject(6).optString("date")%></button>

								</h5>
							</div>
							<div id="collapseSeven" class="collapse"
								 aria-labelledby="headingSix" data-parent="#accordion">
								<div class="card-body"
									 style="max-height: 350px; overflow-y: auto;">
									<table
											class="table table-dark table-bordered table-striped table-hover">
										<thead>
										<tr>
											<th scope="col">Time</th>
											<th scope="col">Condition</th>
											<th scope="col">Temperature (°F)</th>
											<th scope="col">Wind Speed Mph</th>
											<th scope="col">Humidity</th>
											<th scope="col">Atmospheric Pressure (in)</th>
											<th scope="col">Precipitation (mm)</th>


										</tr>
										</thead>
										<tbody>
										<%
											for (int i = 0; i < forecastdayArray.optJSONObject(6).optJSONArray("hour").length(); i++) {
										%>
										<tr>
											<td><%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).getString("time").substring(10)%></td>
											<td><img style="height: 30px; width: 30px"
													 src=<%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).optJSONObject("condition")
		.getString("icon")%>></td>
											<td><%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).getFloat("temp_f")%></td>
											<td><%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).getFloat("wind_mph")%></td>

											<td><%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).getFloat("humidity")%></td>
											<td><%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).getFloat("pressure_in")%></td>
											<td><%=forecastdayArray.optJSONObject(6).optJSONArray("hour").optJSONObject(i).getFloat("precip_mm")%></td>


										</tr>
										<%
											}
										%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- date -->
				<!--alert  -->
				<div>
					<%
					if (alerts.getJSONArray("alert").length() == 0) {
					%>
					<div class="alert alert-success" role="alert">
						<h4 class="alert-heading">Alerts!</h4>
						<p>Currently, there are no alerts to display. Please check
							back later for updates.</p>
						<hr>
						<p class="mb-0">Have a good day!</p>
					</div>
					<%
					} else {
					%>
					<div class="alert alert-danger" role="alert"
						style="height: 200px; overflow-y: scroll">
						<h4 class="alert-heading">Alerts!</h4>
						<h5><%=alerts.optJSONArray("alert").getJSONObject(0).getString("headline")%></h5>
						<hr>
						<p class="mb-0">
							Category:
							<%=alerts.optJSONArray("alert").optJSONObject(0).getString("category")%></p>
						<br>
						<p class="mb-0">
							Effective:
							<%=alerts.optJSONArray("alert").optJSONObject(0).getString("effective").substring(0, 10)%></p>
						<br>
						<p class="mb-0">
							Expires:
							<%=alerts.optJSONArray("alert").optJSONObject(0).getString("expires").substring(0, 10)%></p>
						<br>
						<p class="mb-0">
							Description:
							<%=alerts.optJSONArray("alert").optJSONObject(0).getString("desc")%></p>
					</div>
					<%
					}
					%>
				</div>
			</div>
		</div>

	</div>
	<%
	} else {
	out.println("Error: Location or current weather data not found.");
	}
	} else {
	out.println("Error: Weather data not found.");
	}
	} else {
	out.println("Error: User data not found.");
	}
	%>
	</div>

	<!-- Bootstrap JS and jQuery (needed for navbar toggling) -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
