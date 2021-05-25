<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');
        

    $stmt = $con->prepare('select BARCODE,CVS_NAME,PRODUCT_NAME,PRODUCT_IMAGE from PRODUCT join CVS on PRODUCT.CVS_CODE = CVS.CVS_CODE where PRODUCT.CVS_CODE = 11111111');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);
    
            array_push($data, 
                array(
        	'BARCODE'=>$BARCODE,
		'CVS_NAME'=>$CVS_NAME,
		'PRODUCT_NAME'=>$PRODUCT_NAME,
		'PRODUCT_IMAGE'=>$PRODUCT_IMAGE
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("barcode"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>
