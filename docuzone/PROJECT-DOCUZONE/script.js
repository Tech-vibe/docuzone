// -------- REGISTER PAGE --------
document.addEventListener("DOMContentLoaded", () => {
  const registerForm = document.querySelector(".register-container form");
  if (registerForm) {
    registerForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const name = registerForm.querySelector(
        "input[placeholder='Enter your full name']"
      ).value;
      const ktuId = registerForm.querySelector(
        "input[placeholder='Enter your KTU ID']"
      ).value;
      const pass = registerForm.querySelector(
        "input[placeholder='Enter password']"
      ).value;
      const confirmPass = registerForm.querySelector(
        "input[placeholder='Confirm password']"
      ).value;

      if (pass !== confirmPass) {
        alert("❌ Passwords do not match!");
        return;
      }

      localStorage.setItem("studentName", name);
      localStorage.setItem("ktuId", ktuId);
      localStorage.setItem("password", pass);

      alert("✅ Registration successful! Please login.");
      window.location.href = "login.html";
    });
  }

  // -------- LOGIN PAGE --------

  // -------- HOME PAGE --------
  if (document.querySelector(".home")) {
    const studentName = localStorage.getItem("studentName") || "Student";
    const welcomeBanner = document.createElement("h2");
    welcomeBanner.textContent = `Welcome, ${studentName}! 👋`;
    welcomeBanner.style.marginTop = "80px";
    welcomeBanner.style.textAlign = "center";
    welcomeBanner.style.color = "#1743e3";
    document.querySelector(".home").prepend(welcomeBanner);
  }

  // -------- NOTES PAGE (Upload & Search) --------
  const uploadBtn = document.querySelector(".card button");
  const uploadInput = document.getElementById("uploadFile");
  const notesList = document.getElementById("notesList");
  const searchInput = document.getElementById("searchNotes");

  if (uploadBtn && uploadInput && notesList) {
    uploadBtn.addEventListener("click", () => {
      const file = uploadInput.files[0];
      if (!file) {
        alert("⚠️ Please select a file to upload.");
        return;
      }

      const li = document.createElement("li");
      const link = document.createElement("a");
      link.href = "#";
      link.textContent = file.name;
      li.appendChild(link);

      const delBtn = document.createElement("button");
      delBtn.textContent = "❌";
      delBtn.style.marginLeft = "10px";
      delBtn.style.border = "none";
      delBtn.style.background = "transparent";
      delBtn.style.cursor = "pointer";
      delBtn.addEventListener("click", () => li.remove());

      li.appendChild(delBtn);
      notesList.appendChild(li);

      uploadInput.value = "";
    });
  }

  // Search filter
  if (searchInput) {
    searchInput.addEventListener("input", () => {
      const query = searchInput.value.toLowerCase();
      document.querySelectorAll("#notesList li").forEach((item) => {
        const text = item.textContent.toLowerCase();
        item.style.display = text.includes(query) ? "flex" : "none";
      });
    });
  }
});
