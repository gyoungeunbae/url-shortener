const shortenButton = document.querySelector("#shorten");
const urlInput = document.querySelector("#urlInput");
const layout = document.querySelector("#layout");
const protocol = "http://";
const host = location.host;
let copyText = "";

function click (urlValue) {
    if (urlValue == "" ) {
        return alert("url을 입력해주세요");
    }
    if (! isURL(urlValue)) {
        return alert("url이 유효하지 않습니다.");
    }
    createShortenUrl(urlValue).then(shortenUrl => {
        createElement(urlValue, shortenUrl);
    });
}

function handleToShorten(event) {
    event.preventDefault()
    const url = urlInput.value;
    urlInput.value = "";
    click(url);
}

function handleToCopy(event) {
    event.preventDefault();
    navigator.clipboard.writeText(copyText).then(
        function(){
            alert("복사 되었습니다.");
        })
        .catch(
            function() {
                alert("복사를 실패했습니다.");
            });
}

function splitText(urlValue) {
    if (urlValue.length >= 30) {
        urlValue = urlValue.substring(0, 30) + "...";
    }
    return urlValue;
}
function isURL(str) {
    let pattern = new RegExp('((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
        '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
    return !!pattern.test(str);
}

function createShortenUrl(urlValue) {
    const response = fetch("/urls/new", {
        headers: {
            'Content-Type': 'application/json'
        },
        method : 'post',
        body : JSON.stringify({
            destination : urlValue
        })
    }).then((response) => response.json())
        .then((data) => {
            return data.shortenUrl;
        });
    return response;
}

function createElement(urlValue, shortenUrl) {
    urlValue = splitText(urlValue);
    console.log("!!" + urlValue);
    const resultLayout = document.createElement("div");
    resultLayout.setAttribute("style", "background: white; border-radius: 15px; margin-top: 10px;" +
        "padding-left: 10px; height: 45px; display: static"
    );
    layout.appendChild(resultLayout);

    const ul = document.createElement("ul");
    const destinationSpan = document.createElement("span");
    ul.style.marginTop = 13 + "px";
    ul.style.display = "inline-block";
    ul.style.paddingLeft = "10px";

    layout.appendChild(resultLayout);
    destinationSpan.style.display = "inline-block";
    destinationSpan.innerText = urlValue;

    resultLayout.appendChild(ul);
    ul.appendChild(destinationSpan);

    const shorten = document.createElement("a");
    shorten.style.display = "inline-block";
    shorten.href = protocol + host + "/to/" + shortenUrl;
    shorten.innerText = protocol + host + "/to/" + shortenUrl;
    shorten.style.marginLeft = "5px";
    shorten.style.color = "gray";
    shorten.id = "shorten";
    copyText = shorten.innerText;
    ul.appendChild(shorten);

    const copyButton = document.createElement("button");
    copyButton.style.display = "inline-block";
    copyButton.innerText = "copy";
    copyButton.style.marginLeft = "5px";
    copyButton.style.background = "lightSkyBlue";
    copyButton.style.borderColor = "transparent";
    copyButton.style.borderRadius = "10px";
    copyButton.style.color = "white"
    copyButton.style.marginRight = "10px";
    ul.appendChild(copyButton);
    copyButton.addEventListener("click", handleToCopy);
}

shortenButton.addEventListener("click", handleToShorten);
