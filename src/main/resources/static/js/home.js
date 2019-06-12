let counter = 0;
let clubsIds = null;

document.addEventListener('DOMContentLoaded', () => {
    const hiddenUserData = document.getElementById('hiddenUserData').innerHTML.replace(/&quot;/g, '\\"');
    console.log(hiddenUserData);
    const hiddenUserDataJson = JSON.parse(hiddenUserData);
    clubsIds = hiddenUserDataJson.clubs;
    console.log(clubsIds);
    if (clubsIds.length === 0) {
        const info = document.createElement('div');
        info.innerHTML = 'No data currently to display :(';
        document.querySelector('#posts').append(info);
        return;
    } else {
        load();
        counter += 1;
    }
    if (counter < clubsIds.length) {
        load();
        couter += 1;
    }
});

window.onscroll = () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
        if (clubsIds != null && counter < clubsIds.length) {
            load();
            counter += 1;
        }
    }
};

document.addEventListener('click', event => {
    const element = event.target;
    if (element.className === 'hide') {
        element.parentElement.style.animationPlayState = 'running';
        element.parentElement.addEventListener('animationend', () =>  {
            element.parentElement.remove();
        });
    }
});

function load() {
    fetch(`https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=${clubsIds[counter]}`)
        .then(response => response.json())
        .then(data => {
            data.results.forEach(add_post)
        });
}

function add_post(contents) {
    const post = document.createElement('div');
    post.className = 'post';
    if (contents.intHomeScore != null && contents.intAwayScore != null) {
        post.innerHTML = contents.strFilename + ' --- Result: (' + contents.intHomeScore + ' : ' + contents.intAwayScore + ')';
    } else {
        post.innerHTML = contents.strFilename;
    }

    const hide = document.createElement('button');
    hide.className = 'hide';
    hide.innerHTML = 'Hide';
    post.append(hide);

    document.querySelector('#posts').append(post);
}