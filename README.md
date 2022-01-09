# Allegro Recruitment Task
A proposition of solving task no 3 - Software Engineer for Spring Tech 
E-Xperience.

## Running  server
In order to start server on local machine download all files from repository, unzip them and then perform next steps:

First option of running:
1. Run file compile.bat or compile.sh by double click or open one of them on the command line running on the folder with files. After successful compilation folder "Target" with produced .jar file will appear.  This operation need to be performed again only if there will be any changes with the code to produce new .jar file. 
2. Run file run.bat or run.sh by double click or open one of them on the command line running on the folder with files. The server then will start.

Second option of running:

Start IDE (IntelliJ for example) and then open pom.xml file as a new project, then start server manually from AllegroRecruitmentTaskApplication.class.

By default port of the server is set to 8083. If this port is already in use you need to change port to free one in "application.yml" file on the src\main\resources folder and then start again server by one of previously mentioned ways. 

By default logs from the application will be saved on directory: "c:/allegroTaskLogs/logs/". If this directory is already used or unavailable you need to change directory in logback.xml file on the src/main/resources/.  

## API description
Server is executing only Get request during which is communicating with GitHub server for fetching data of user repositories. According to the response is performing his actions. Endpoints of the server (with defult port) : 

- http://localhost:8083/githubclient/api/repos/{GitHubUsername}

If user is valid and has public repositories server in response will produce JSON with user repositories and their ratings from the top rated to the lowers. In case if user is not valid or doesn't have any public repositories proper message will be returned by server in response and in logs.

- http://localhost:8083/githubclient/api/rating/{GitHubUsername}

If user is valid and has public repositories server will produce JSON with total rating of the user public repositories. In case if user is not valid or doesn't have any public repositories proper message will be returned by server in response and in logs.

- http://localhost:8083/githubclient/api/languages/{GitHubUsername}

If user is valid and has public repositories server in response will produce JSON with programming languages of user repositories and sum their total size. Results will be presented from the biggest to the lowest. In case if user is not valid or doesn't have any public repositories proper message will be returned by server in response and in logs.

## Proposition for further develop of application

To already created software could be added a temporary database like H2 to run as in-memory database during application working. GitHub is a service where data could change very quickly (for example: users could create new repositories or delete already existed) so there is no point of storing it for longer time. During work of application information about 
GitHub user account and theirs repositories will be saved in database so later using a properly implemented services and endpoints application user could get information like which GitHub account has the highest rated repository or which one has more public repositories.     
