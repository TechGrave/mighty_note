import mysql.connector

import CRUD as crud


def openConnection():
    myDB = mysql.connector.connect(user="codebuddy", password="", host="127.0.0.1", port=3307)
    # use 3306 for other (your) system(s), no password
    if myDB is not None:
        return myDB
    else:
        return False


def checkForDatabase(myCursor):
    try:
        myCursor.execute("CREATE DATABASE mightynote")
    except:
        pass


def checkForTables(myCursor):
    print('checkForTables()')
    try:  # user table
        print('user checking')
        print("'book' table created")
        # setting start value for shop_id
        myCursor.execute(
            "CREATE TABLE `mightynote`.`user`(`id` INT NOT NULL AUTO_INCREMENT,"
            "`name` VARCHAR(100) NOT NULL, `password` VARCHAR(100) NULL,"
            "`email` VARCHAR(100) NOT NULL, PRIMARY KEY(`id`),"
            "UNIQUE INDEX `email_UNIQUE`(`email` ASC) VISIBLE);"
        )
        myCursor.execute(
            "ALTER TABLE `mightynote`.`user` AUTO_INCREMENT = 100000;"
        )
        print('user created')
    except BaseException as err:
        print(err)
        pass


def login(email, password):
    try:
        myDB = openConnection()
        myCursor = myDB.cursor(dictionary=True)
        myCursor.execute(
            "SELECT `password` FROM `mightynote`.`user` WHERE email = '" + email + "';"
        )
        res = myCursor.fetchall()
        storedPassword = ''

        for x in res:
            storedPassword = x['password']
            break

        if storedPassword == password:
            return True
        else:
            return False
    except mysql.connector.Error as err:
        print("MySQL Error:")
        print(err)
        return False
    except BaseException as error:
        print("Error:" + error)
        return False


def start():
    print('inside DatabaseHandling.start()')
    myDB = openConnection()

    if myDB is not None:
        myCursor = myDB.cursor()
        print('start()')
        print(myDB)
        checkForDatabase(myCursor)
        checkForTables(myCursor)
        myDB.commit()
    else:
        print('Database not connected.')


# other functions via this class
def createAccount(name, email, password):
    myDB = openConnection()
    return crud.createAccount(myDB, name, email, password)


def addNotebook(subject, teacher, className):
    myDB = openConnection()
    return crud.addNotebook(myDB, subject, teacher, className)


def getNotebookDetails(subject):
    myDB = openConnection()
    return crud.getNotebookDetails(myDB, subject)
