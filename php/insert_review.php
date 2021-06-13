<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    if( ($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit']))
    {

        $BARCODE=$_POST['BARCODE'];
        $PRODUCT_POINT=$_POST['PRODUCT_POINT'];
        $USER_INFO_ID=$_POST['USER_INFO_ID'];
        $COMMENTS=$_POST['COMMENTS'];


        if(!isset($errMSG))
        {
            try{
                $sql = "INSERT INTO PRODUCT_REVIEW(BARCODE, PRODUCT_POINT,USER_INFO_ID,COMMENTS) VALUES(:BARCODE, :PRODUCT_POINT,:USER_INFO_ID, :COMMENTS); UPDATE PRODUCT SET AVE_GRADE=(SELECT AVG(PRODUCT_POINT) FROM PRODUCT_REVIEW WHERE BARCODE = :BARCODE) WHERE BARCODE = :BARCODE;";
                $stmt = $con->prepare($sql);
                $stmt->bindParam(':BARCODE', $BARCODE);
                $stmt->bindParam(':PRODUCT_POINT', $PRODUCT_POINT);
                $stmt->bindParam(':USER_INFO_ID', $USER_INFO_ID);
                $stmt->bindParam(':COMMENTS', $COMMENTS);

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
            BARCODE: <input type = "text" name = "BARCODE" />
            PRODUCT_POINT: <input type = "text" name = "PRODUCT_POINT" />
            USER_INFO_ID: <input type = "text" name = "USER_INFO_ID" />
            COMMENTS: <input type = "text" name = "COMMENTS" />
            <input type = "submit" name = "submit" />
        </form>
   
   </body>
</html>
