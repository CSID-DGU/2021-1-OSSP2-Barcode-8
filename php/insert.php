<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    if( ($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit']))
    {

        $CVS_CODE=$_POST['CVS_CODE'];
        $CVS_NAME=$_POST['CVS_NAME'];

        if(empty($CVS_CODE)){
            $errMSG = "이름을 입력하세요.";
        }
        else if(empty($CVS_NAME)){
            $errMSG = "나라를 입력하세요.";
        }

        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO CVS(CVS_CODE, CVS_NAME) VALUES(:CVS_CODE, :CVS_NAME)');
                $stmt->bindParam(':CVS_CODE', $CVS_CODE);
                $stmt->bindParam(':CVS_NAME', $CVS_NAME);

                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }
?>

<html>
   <body>
        <?php 
        if (isset($errMSG)) echo $errMSG;
        if (isset($successMSG)) echo $successMSG;
        ?>
        
        <form action="<?php $_PHP_SELF ?>" method="POST">
            CVS_CODE: <input type = "text" name = "CVS_CODE" />
            CVS_NAME: <input type = "text" name = "CVS_NAME" />
            <input type = "submit" name = "submit" />
        </form>
   
   </body>
</html>
