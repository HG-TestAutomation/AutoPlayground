function removeListItems() {
  const items = document.querySelectorAll(".list-item");
  items.forEach(item => item.remove());
}

function updateGender() {
  const radios = document.getElementsByName("gender");
  for (let radio of radios) {
    if (radio.checked) {
      document.getElementById("radioResult").textContent = radio.value;
      break;
    }
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("loginForm");
  const error = document.getElementById("errorMessage");

  form.addEventListener("submit", function (e) {
    e.preventDefault(); // Always prevent default browser behavior

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    if (username === "admin" && password === "password") {
      error.textContent = "";
      window.location.href = "home.html";
    } else {
      error.textContent = "Invalid username or password";
      error.style.color = "red";
      error.style.display = "block";
    }
  });
});


