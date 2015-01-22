<?php
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $tag = $_POST['tag'];
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
    $response = array("tag" => $tag, "success" => 0, "error" => 0);
 
    if ($tag == 'login') {
        $email = $_POST['email'];
        $password = $_POST['password'];
 
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user != false) {
            $response["success"] = 1;
                $response["uid"] = $user["unique_id"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["roll"] = $user["roll"];
                $response["user"]["branch"] = $user["branch"];
                $response["user"]["year"] = $user["year"];
                $response["user"]["div"] = $user["division"];
                $response["user"]["email"] = $user["email"];
                
            echo json_encode($response);
        } else {
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    }

    else if ($tag == 'register') {
        $name = $_POST['name'];
        $roll = $_POST['roll'];
        $branch = $_POST['branch'];
        $year = $_POST['year'];
        $div = $_POST['div'];
        $email = $_POST['email'];
        $password = $_POST['password'];
 
        if ($db->isUserExisted($email)) {
            $response["error"] = 2;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } else {
            $user = $db->storeUser($name,$roll,$branch,$year,$div,$email,$password);
            if ($user) {
                $response["success"] = 1;
                $response["uid"] = $user["unique_id"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["roll"] = $user["roll"];
                $response["user"]["branch"] = $user["branch"];
                $response["user"]["year"] = $user["year"];
                $response["user"]["div"] = $user["division"];
                $response["user"]["email"] = $user["email"];

                echo json_encode($response);
            } else {
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    }

    else if($tag=='notice')
    {
        $branch=$_POST['branch'];
        $year=$_POST['year'];
        $div=$_POST['div'];

        $notice_table=$db->getAllNotices($branch, $year, $div);
        $response['notice']=$notice_table;
        echo json_encode($response);
    }

    else {
        echo "Invalid Request";
    }
} else {
    echo "Access Denied";
}
?>