# Project 1: Mobile Company

##Description

The Mobile Company data for clients and tariffs is stored as text files. This data is loaded into the system and user can filter out information on tariffs using a simple command-line interface.

## Tools used

- Maven
- JSON library www.json.org/java/
- Log4j2
- SonarQube

## Things which could be improved

- Currently all the data is stored as text files using JSON objects. The clients JSON strings should one line per entry (as it allowed for writing less code). Thus, it is possible to use `javax.json` instead and loosen such requirements on the text formatting. Ideally, the data should be stored in a database.
- The command-line interface can also be enhanced. For example, by providing more commands and more sofisticated filters.
