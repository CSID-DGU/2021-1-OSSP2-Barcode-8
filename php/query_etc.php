<?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');



//POST 값을 읽어온다.
$PRODUCT_NAME=isset($_POST['PRODUCT_NAME']) ? $_POST['PRODUCT_NAME'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($PRODUCT_NAME != "" ){ 

    $sql="select BARCODE,CVS_NAME,PRODUCT_NAME,PRODUCT_IMAGE from PRODUCT join CVS on PRODUCT.CVS_CODE = CVS.CVS_CODE where PRODUCT.CVS_CODE = 12345678 AND PRODUCT_NAME LIKE '%$PRODUCT_NAME%'";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){

        echo "'";
        echo $PRODUCT_NAME;
        echo "'은 찾을 수 없습니다.";
    }
	else{

   		$data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

        	extract($row);

            array_push($data, 
            array(
            'BARCODE'=>$BARCODE,
            'CVS_NAME'=>$CVS_NAME,
            'PRODUCT_NAME'=>$PRODUCT_NAME,
            'PRODUCT_IMAGE'=>$PRODUCT_IMAGE
            ));
        }


        if (!$android) {
            echo "<pre>"; 
            print_r($data); 
            echo '</pre>';
        }else
        {
            header('Content-Type: application/json; charset=utf8');
            $json = json_encode(array("barcode"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
        }
    }
}
else {
    echo "검색할 제품명을 입력하세요 ";
}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         제품명 : <input type = "text" name = "PRODUCT_NAME" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>