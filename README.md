![](https://github.com/SoloJan/checkers/workflows/tests/badge.svg)

#About 
This repository contains two projects both implement a checkers game. One is a pure backend project, the other is a pure
frontend project the two are not connected at all. The backend is a serious project written in the techniques I use and 
like. The frontend is just a little joke on the side I wanted to try out the static pages which github recently added. 
I'm very aware that the code in the frontend is not good I would not even do it this way if I would make a frontend, 
but it was easy to make and nice to have something visual to play with. 

# Checkers for nerds
Checkers for nerds is very intuitive but only if you are used to working with REST api's. It has the latest 
and greatest frameworks. It runs on multiple database types including an in memory database which just works automatically.  
And the database is kept up to date with flyway, so you never have any problem migrating between versions. 
It uses spring boot witch means that it comes with a build in tomcat webserver. 
It has swagger as documentation of the api's which means you do not have to use postman or any other tool to  interact 
with the api's, You might prefer using curls from the commandline but swagger generates those for you as well. 

I implemented a ci/cd pipeline in github which runs the unit test and checks for 90% coverage.

There is no user interface but feel free to build one. You can play against a friend, or you could build a client 
which acts as a player and play against it after all it is a rest api, who knows who goes behind player2.

You probably not really into playing checkers and you probably just start looking at the source code but if you really 
want to play start reading the readme in the backend project on how to get started.

# Checkers for managers
There is also a frontend project which is basically a static HTML5 file with some javascript. 
I just felt like playing around and have something visual to show as well. The thing I like about it is that its extremely
lightweight, just clicking the HTML in the frontend project does the trick. I also added a ci/cd pipeline which 
deploys the frontend to git hub pages. This means that you can view the end result online

https://solojan.github.io/checkers/  

The frontend and the backend have nothing to do with each other, they are two separate implementations of the same game

The code in the frontend is not of very high quality there are many ways of improving on it.
