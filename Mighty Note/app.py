from flask import Flask
import DatabaseHandling as dh
from ToJSON import toJsonFormat as asJson

app = Flask(__name__)


@app.route('/')
def hello_world():
    return asJson('Hello World!')


@app.route('/signup/<string:name>/<string:email>/<string:password>/<string:confirmPassword>')
def createAccount(name, email, password, confirmPassword):
    # creates new account
    name = name.lower().strip()
    email = email.lower().strip()

    if password == confirmPassword:
        return asJson(dh.createAccount(name, email, password))
    else:
        return asJson('Passwords did not match!')
    pass


@app.route('/add/notebook/<string:subject>/<string:teacher>/<string:className>')
def addNotebook(subject, teacher, className):
    subject = subject.lower().strip()
    teacher = teacher.lower().strip()
    className = className.lower().strip()

    return asJson(dh.addNotebook(subject, teacher, className))
    pass


@app.route('/notebook/details/<string:subject>')
def getNotebookDetails(subject):
    subject = subject.strip().lower()
    return dh.getNotebookDetails(subject)
    pass


@app.route('/login/<string:email>/<string:password>')
def login(email, password):
    # logging in
    email = email.lower().strip()
    return asJson(dh.login(email, password))
    pass


dh.start()
app.run(debug=True)
