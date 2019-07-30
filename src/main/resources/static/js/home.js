let counter = 0;
let clubsIds = null;
let leaguesIds = null;

document.addEventListener('DOMContentLoaded', () => {

    const hiddenUserData = document.getElementById('hiddenUserData').innerHTML.replace(/&quot;/g, '\\"');
    console.log(hiddenUserData);
    const hiddenUserDataJson = JSON.parse(hiddenUserData);
    clubsIds = hiddenUserDataJson.clubs;
    console.log(clubsIds);

    const hiddenUserDataLeagues = document.getElementById('hiddenUserDataLeagues').innerHTML.replace(/&quot;/g, '\\"');
    console.log(hiddenUserDataLeagues);
    const hiddenUserDataLeaguesJson = JSON.parse(hiddenUserDataLeagues);
    leaguesIds = hiddenUserDataLeaguesJson.leagues;
    console.log(leaguesIds);

    if (clubsIds.length === 0 && leaguesIds.length === 0) {
        const info = document.createElement('div');
        info.innerHTML = 'No data currently to display :(';
        document.querySelector('#posts').append(info);
        return;
    } else {
        load();
        counter += 1;
    }
    if (counter < clubsIds.length + leaguesIds.length) {
        load();
        counter += 1;
    }
});

window.onscroll = () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
        if ((clubsIds != null || leaguesIds != null )&& counter < clubsIds.length + leaguesIds.length) {
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

    let apiUrl = null;
    let pathToLoad;

    if(counter >= clubsIds.length){
        apiUrl = `https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=${leaguesIds[counter-clubsIds.length]}`;
        pathToLoad = `leagues`;
    }
    else{
        apiUrl = `https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=${clubsIds[counter]}`;
        pathToLoad = `clubs`;
    }

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if(pathToLoad === `clubs`){
                data.results.forEach(add_post)
            }
            else{
                data.events.forEach(add_post)
            }
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

    const hide = document.createElement('div');
    hide.className = 'hide';
    hide.innerHTML = 'Hide';
    post.append(hide);

    document.querySelector('#posts').append(post);
}