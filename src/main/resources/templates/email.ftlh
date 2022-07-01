<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>Spring Boot Email using FreeMarker</title>
</head>
<body>
<div style="margin-top: 10px">Bonjour, ${prenom} ${nom}</div>
<div style="margin-top: 10px">
    Merci de clicker sur le lien pour confirmer votre inscription : <a href="${urlvalidation}">Valider votre email</a>
</div>
<br/>
<div style="margin-top: 50px">Equipe DiniStop</div>
</body>
</html>