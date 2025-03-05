<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // Load states dynamically from servlet
            $.ajax({
                url: 'StateServlet',
                type: 'GET',
                success: function(response) {
                    $('#state').append(response);
                },
                error: function() {
                    alert('Error loading states.');
                }
            });

            $('#state').change(function() {
                var stateId = $(this).val();
                if (stateId) {
                    $.ajax({
                        url: 'CityServlet',
                        type: 'GET',
                        data: { state: stateId }, // Fixed key name
                        success: function(response) {
                            $('#city').html('<option value="">Select city</option>' + response);
                        },
                        error: function() {
                            $('#city').html('<option value="">Select a state first...</option>'); // Reset on error
                            alert('Error loading cities.');
                        }
                    });
                } else {
                    $('#city').html('<option value="">Select a state first...</option>');
                }
            });

            // File validation 
            $("#image").change(function() {
                var file = this.files[0];
                if (file) {
                    var fileType = file.type;
                    var validTypes = ["image/jpeg", "image/png", "image/jpg"];

                    if ($.inArray(fileType, validTypes) < 0) {
                        alert("Invalid file type! Only JPG, PNG images are allowed.");
                        $("#image").val('');
                        return;
                    }
                    if (file.size > 2 * 1024 * 1024) { // 2MB limit
                        alert("File size exceeds 2MB!");
                        $("#image").val('');
                        return;
                    }
                }
            });

            // Password validation
            $('#password').on('input', function() {
                var pass = $(this).val();
                if (pass.length < 6) {
                    $('#passwordError').text('Password must be at least 6 characters long.');
                } else {
                    $('#passwordError').text('');
                }
            });

            // Phone number validation (Only 10 digits)
            $('#phone').on('input', function() {
                var phone = $(this).val();
                if (!/^\d{10}$/.test(phone)) {
                    $('#phoneError').text('Enter a valid 10-digit phone number.');
                } else {
                    $('#phoneError').text('');
                }
            });
        });
    </script>
</head>
<body>
    <div class="container">
        <h1>Student Registration</h1>
        <form action="RegisterServlet" method="post" enctype="multipart/form-data">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>   

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <span id="passwordError" style="color: red;"></span><br>

            <label for="phone">Phone Number:</label>
            <input type="tel" id="phone" name="phone" required>
            <span id="phoneError" style="color: red;"></span><br>

            <label for="dob">Date of Birth:</label>
            <input type="date" id="dob" name="dob" required><br>

            <label for="state">State:</label>
            <select id="state" name="state" required>
                <option value="">Choose...</option>
               
            </select><br>

            <label for="city">City:</label>
            <select id="city" name="city" required>
                <option value="">Choose a state first...</option>
               
            </select><br>

            <!-- 🔹 Image Upload Field -->
            <label for="image">Upload Image:</label>
            <input type="file" id="image" name="image" accept="image/*" required><br>

            <button type="submit">Register</button>
        </form>

        <p><a href="Index.html">Back to Home</a></p>

        <%-- Display error message if registration fails --%>
        <% String error = request.getParameter("error");
           if (error != null) { 
               if (error.equals("missing_fields")) { %>
                   <div style="color: red; font-weight: bold;">Please fill all required fields.</div>
        <%     } else if (error.equals("insert_failed")) { %>
                   <div style="color: red; font-weight: bold;">Registration failed. Please try again.</div>
        <%     } else if (error.equals("invalid_state_city")) { %>
                   <div style="color: red; font-weight: bold;">Invalid state or city selection.</div>
        <%     } else if (error.equals("sql_error")) { %>
                   <div style="color: red; font-weight: bold;">Database error occurred. Contact support.</div>
        <%     } else { %>
                   <div style="color: red; font-weight: bold;">Unknown error. Try again.</div>
        <%     }
           } %>

        <%-- Display success message if registration is successful --%>
        <% String success = request.getParameter("success");
           if (success != null && success.equals("1")) { %>
            <div style="color: green; font-weight: bold;">Registration successful!</div>
        <% } %>
    </div>
</body>
</html>
