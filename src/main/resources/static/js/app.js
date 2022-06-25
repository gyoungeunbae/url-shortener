const shortenButton = document.querySelector("#shorten");
const urlInput = document.querySelector("#urlInput");
const layout = document.querySelector("#layout");

function shorten(urlValue) {
    if (urlValue == "" ) {
        return alert("url을 입력해주세요");
    }
    if (! isURL(urlValue)) {
        return alert("url이 유효하지 않습니다.");
    }

    // todo :: api 호출..
    const resultLayout = document.createElement("div");
    resultLayout.setAttribute("style", "background: white; border-radius: 15px; margin-top: 10px;" +
        "padding-left: 20px; height: 45px; display: static"
    );
    layout.appendChild(resultLayout);

    const ul = document.createElement("ul");
    const span = document.createElement("span");
    ul.style.marginTop = 13 + "px";
    ul.style.display = "inline-block";
    ul.style.paddingLeft = "10px";

    layout.appendChild(resultLayout);
    span.style.display = "inline-block";
    span.innerText = urlValue;

    resultLayout.appendChild(ul);
    ul.appendChild(span);
    console.log("shorten!!");
}

function handleToShorten(event) {
    event.preventDefault()
    const url = urlInput.value;
    urlInput.value = "";
    shorten(url);
}

function isURL(str) {
    let pattern = new RegExp('((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
        '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
    console.log(!!pattern.test(str));
    return !!pattern.test(str);
}

shortenButton.addEventListener("click", handleToShorten);
