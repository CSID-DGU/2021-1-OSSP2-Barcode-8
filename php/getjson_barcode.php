<?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');



//POST 값을 읽어온다.
$BARCODE=isset($_POST['BARCODE']) ? $_POST['BARCODE'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($BARCODE != "" ){ 

    $sql="select BARCODE,CVS_NAME,PRODUCT_NAME,PRODUCT_IMAGE from PRODUCT join CVS on PRODUCT.CVS_CODE = CVS.CVS_CODE where BARCODE = '$BARCODE'";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){

        echo "'";
        echo $BARCODE;
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
    echo "검색할 바코드를 입력하세요 ";
}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         바코드 : <input type = "text" name = "BARCODE" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>