<?php
 
 require_once 'config.php';
 $con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

class DB_Functions {
 
    private $db;
    function __construct() {
        
    }
 
    function __destruct() {
         
    }

    public function storeUser($name,$roll,$branch,$year,$div,$email,$password) {
        global $con;
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"];
        $salt = $hash["salt"];
        $result = mysqli_query($con,"INSERT INTO allusers(unique_id,name,roll,branch,year,division,email,password,salt) VALUES('$uuid', '$name', '$roll', '$branch', '$year', '$div', '$email', '$encrypted_password', '$salt')");
        
        if ($result) {
        
            $uid = mysqli_insert_id($con);
            $resultsel = mysqli_query($con,"SELECT * FROM allusers WHERE uid = $uid");
        
            return mysqli_fetch_array($resultsel,MYSQLI_ASSOC);
        } else {
            return false;
        }
    }
 
    public function getUserByEmailAndPassword($email, $password) {
        global $con;
        $result = mysqli_query($con,"SELECT * FROM allusers WHERE email = '$email'") or die(mysqli_error($con));
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result,MYSQLI_ASSOC);
            $salt = $result['salt'];
            $encrypted_password = $result['password'];
            $hash = $this->checkhashSSHA($salt, $password);
            if ($encrypted_password == $hash) {
                return $result;
            }
        } else {
            return false;
        }
    }
 
    public function isUserExisted($email) {
        global $con;
        $resultuser = mysqli_query($con,"SELECT email from allusers WHERE email = '$email'");
        $no_of_rows_user = mysqli_num_rows($resultuser);
        if ($no_of_rows_user > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public function getAllNotices($branch, $year, $div)
    {
        global $con;
        $table_name=$branch."_".$year;
        $query ="SELECT * from $table_name WHERE division = '$div' OR division='ALL'";
        $res = mysqli_query($con, $query);

        if(!$res) {
            die(json_encode(array("error" => "no results from table")));
        }
        $fields_num = mysqli_num_fields($res);
        for($i=0; $i < $fields_num; $i++) {
            $field = mysqli_fetch_field($res);
        }

        $i = 0;
        while($row = mysqli_fetch_array($res,MYSQLI_ASSOC)) {
            $rows[$i] = $row;
            $i++;
        }
        
        return $rows;

    }

    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
}
 
?>