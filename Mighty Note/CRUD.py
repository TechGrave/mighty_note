import mysql


def createAccount(myDB, name, email, password):
    print('end')
    myDB.autocommit = False
    cursor = myDB.cursor(dictionary=True)
    try:
        cursor.execute(
            "INSERT INTO `mightynote`.`user`(`name`, `password`, `email`) " +
            "VALUES('" + name + "', '" + password + "', '" + email + "');"
        )
        print('inserted into user')

        # get user_id
        cursor.execute(
            "SELECT `id` FROM `mightynote`.`user` WHERE `email` = '" + email + "';"
        )
        user_id = ''
        for x in cursor:
            user_id = str(x['id'])
            if user_id is not None:
                break

        print('user_id: ' + user_id)

        # create user_book
        print('creating book')
        cursor.execute(
            "CREATE TABLE `mightynote`.`user_book_" + user_id + "`("
            "`book_id` INT NOT NULL AUTO_INCREMENT, `subject` VARCHAR(100) NULL,"
            "`teacher` VARCHAR(100) NULL, `type` VARCHAR(100) NULL,"
            "`class` VARCHAR(100) NULL, `format` VARCHAR(100) NULL,"
            "`date` VARCHAR(100) NULL, PRIMARY KEY(`book_id`));"
        )

        print('book table created')

        # create user_page
        print('creating page')
        cursor.execute(
            "CREATE TABLE `mightynote`.`user_page_" + user_id + "`( "
            "`page_id` INT NOT NULL AUTO_INCREMENT, `book_id` INT NOT NULL, "
            "`filename` VARCHAR(100) NOT NULL, PRIMARY KEY(`page_id`));"
        )
        print('page created')
        myDB.commit()
        print(True)
        return True
    except mysql.connector.Error as err:
        print("MySQL Error:")
        print(err)
        myDB.rollback()
        return False
    except BaseException as error:
        print("Error:" + error)
        myDB.rollback()
        return False
    finally:
        myDB.autocommit = True


def addNotebook(myDB, subject, teacher, className):
    try:
        myCursor = myDB.cursor()
        myCursor.execute(
            "INSERT INTO `mightynote`.`user_book_100000`(`subject`, `teacher`, `class`, `date`) VALUES ('" + subject + "', '" + teacher + "', '" + className + "', CURDATE());"
        )
        myDB.commit()
        return True
    except mysql.connector.Error as err:
        print("MySQL Error:")
        print(err)
        return False
    except BaseException as error:
        print("Error:" + str(error))
        return False


def getNotebookDetails(myDB, subject):
    try:
        myCursor = myDB.cursor(dictionary=True)
        myCursor.execute(
            "SELECT `book_id`, `subject`, `teacher`, `class`, `date` FROM `mightynote`.`user_book_100000` WHERE subject = '" + subject + "';"
                         )

        row_headers = [x[0] for x in myCursor.description]  # this will extract row headers

        result = myCursor.fetchall()
        for y in result:
            print(y)
            # converting to JSON format
            return y

        return False    # if no such account is found return False
    except mysql.connector.Error as err:
        print("MySQL Error:")
        print(err)
        return False
    except BaseException as error:
        print("Error:" + error)
        return False