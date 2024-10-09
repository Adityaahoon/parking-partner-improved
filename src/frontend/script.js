// Handle login form submission
document.getElementById("loginForm")?.addEventListener("submit", function(event) {
    event.preventDefault();

    // Get input values
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Simulate backend validation (you would call an API in a real application)
    if (username === "testUser" && password === "testPass") {
        // Redirect to the main menu page
        window.location.href = "mainMenu.html";
    } else {
        alert("Invalid username or password. Please try again.");
    }
});

// Handle registration form submission
document.getElementById("registerForm")?.addEventListener("submit", function(event) {
    event.preventDefault();

    // Get input values
    const regUsername = document.getElementById("regUsername").value;
    const regEmail = document.getElementById("regEmail").value;
    const regPassword = document.getElementById("regPassword").value;
    const regContact = document.getElementById("regContact").value;

    // Simulate registration (you would send data to an API in a real application)
    alert("Registration successful for user: " + regUsername);
    // Redirect to login page after registration
    window.location.href = "login.html";
});

// Main menu interaction
document.querySelectorAll("ul li a").forEach(function(menuItem) {
    menuItem.addEventListener("click", function(event) {
        event.preventDefault();
        alert("You selected: " + this.innerText);
        // Implement actual features like view profile, book spot, etc.
    });
});
