function ajaxRegister(){
    let xhr = new XMLHttpRequest();
    let form = document.getElementById("id_form");
    let info = document.getElementById("id_info");
    xhr.onreadystatechange = function () {
        if(xhr.status === 200 && xhr.readyState === 4){
            console.log(xhr.responseText);
            form.hidden = true;
            info.hidden = false;
        }
    };
    /*Json transmission*/

    xhr.open("POST", "ajax", true);
    let json = formToJson(form);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    xhr.send(""+formToJson(form));
    console.log(formToJson(form));

    /*Form transmission*/
    /*
    xhr.open("POST", "index", true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
    let formdata = formToFormData(form);
    xhr.send(formdata);
     */
}

function formToJson(form){
    let elements = [];
    //get all tags
    let tagElements = form.getElementsByTagName("input");
    //combine atom K-V s
    for(let i=0; i<tagElements.length; i++){
        if(tagElements[i].type.toLowerCase() !== 'button') {
            elements.push("\""+tagElements[i].name + "\":\"" + tagElements[i].value + "\"");
        }
    }
    //reform a JSON string
    elements = "{" + elements.join(",") + "}";
    return elements;
}

function formToFormData(form){
    let formdata = new FormData();
    //get all tags
    let tagElements = form.getElementsByTagName("input");
    //get atom K-Vs
    for(let i=0; i<tagElements.length; i++){
        if(tagElements[i].type.toLowerCase() !== 'button') {
            formdata.append(tagElements[i].name, tagElements[i].value)
        }
    }
    return formdata;
}