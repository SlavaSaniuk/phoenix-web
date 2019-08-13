
    var post_xhttp = new XMLHttpRequest();

    function initializeEventListeners() {

        //Disable form submission
        var form = document.getElementById("post_form");

        //Execute ajax request
        form.addEventListener("submit", function (ev) {
            ev.preventDefault();
            createPost();
        });
        
    }

    function createPost() {

        post_xhttp.open("POST", "/phoenix_web/async_create_post", true);
        post_xhttp.send("Hello");

    }

