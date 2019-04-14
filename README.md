# Football-Assistant
Web App providing helpful info for football fans

Created for a course at AGH-UST (Technologies of Internet Applications 2019)

Authors:
- Dawid Kulma
- Patryk Węgrzyn


Notes:
- Spring Data automatically exposes CRUD endpoints for domain classes (e.g. /footballClubs)
- Actuator is up (/actuator/{endpoint}, endpoints: health, info, beans, mappings, etc)
- All the security dependencies are commented-out for now, same with Thymeleaf
- Some endpoints will have to be exposed manually with MVC (when additional logic is needed instead of only CRUD)
- Spring Devtools takes care of automatically reloading the app after changes are detected (e.g. in Intellij you only need to do Build -> Build Project)
