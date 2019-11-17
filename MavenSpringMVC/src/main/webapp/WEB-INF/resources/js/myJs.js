function ajaxRegister(){
    let xhr = new XMLHttpRequest();
    let form = document.getElementById("id_form");
    let formdata = new FormData(form);
    let info = document.getElementById("id_info");
    xhr.onreadystatechange = function () {
        if(xhr.status === 200 && xhr.readyState === 4){
            console.log(xhr.responseText);
            form.hidden = true;
            info.hidden = false;
        }
    };
    xhr.open("POST", "index", true);
    xhr.send(JSON.stringify(formdata));
}