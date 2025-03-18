// Initialize when the page loads
window.onload = function () {
    setNewestEditionButton();
    filterSelection("all");
};

// Function to filter newsletters based on the selected year
function filterSelection(year, event) {
    var newsletters = document.getElementsByClassName("newsletter-box");
    for (var i = 0; i < newsletters.length; i++) {
        newsletters[i].style.display =
            year === "all" || newsletters[i].classList.contains(year)
                ? "block"
                : "none";
    }

    // Update active button
    var buttons = document.getElementsByClassName("btn");
    for (var j = 0; j < buttons.length; j++) {
        buttons[j].classList.remove("active");
    }
    event.currentTarget.classList.add("active");
}

// Show all by default
filterSelection("all");

// Function to find the newsletter with the highest number
function getNewestEdition() {
    const newsletters = document.querySelectorAll(".newsletter-box");
    let highestNumber = 0;
    let newestEdition = null;

    newsletters.forEach((newsletter) => {
        // Extract the number from the filename
        const pdfFile = newsletter.getAttribute("data-pdf");
        const filenameMatch = pdfFile.match(/(\d{2})_/); // Match the first two digits

        if (filenameMatch) {
            const number = parseInt(filenameMatch[1], 10);

            // Compare the number and track the highest
            if (number > highestNumber) {
                highestNumber = number;
                newestEdition = newsletter;
            }
        }
    });

    return newestEdition;
}

// Function to update the "Newest Edition" button with the latest PDF
function setNewestEditionButton() {
    const newestEdition = getNewestEdition();
    const newestEditionBtn = document.getElementById("newestEditionBtn");

    if (newestEdition && newestEditionBtn) {
        // Update button text to display the newest edition label
        newestEditionBtn.textContent = `Nyeste Udgave: ${newestEdition.textContent.trim()}`;

        // Set the click handler to open the newest newsletter
        newestEditionBtn.onclick = function () {
            const fileName = newestEdition.getAttribute("data-pdf");
            window.open(fileName, "_blank");
        };
    }
}

// Add event listeners to the filter buttons to activate the filter
var btnContainer = document.getElementById("filterButtons");
var btns = btnContainer.getElementsByClassName("btn");
for (var i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function (event) {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";

        // Check for specific button content and pass the correct argument
        if (this.textContent.trim() === "Vis Alle") {
            filterSelection("all", event); // Show all newsletters when "Vis Alle" is clicked
        } else {
            filterSelection(this.textContent.trim(), event); // Filter based on year
        }
    });
}
