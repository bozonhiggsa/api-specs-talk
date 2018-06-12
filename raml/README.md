### To generate documentation:

#### raml2html
```
npm install -g raml2html

raml2html -i api.raml -o api.html
```

#### raml api-console
```
npm install -g api-console-cli

api-console build ./api.raml -o ./api-console/

api-console serve ./api-console

open http://127.0.0.1:8081
```