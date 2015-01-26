$(document).ready(function(){
    
    $('#activityBody').hide();
    $(window).resize(function(e){
    // your code
        e.preventDefault();
    var windowWidth = $(window).width();
    
    var mainContainerWidth = windowWidth-100; // For example
    $("#container").addClass('{"width":mainContainerWidth'+'"px"}');
    $('html').addClass('{"height":'+ $(window).height() +' "px"}');
    });

    $('#registrationForm').hide();
    $('#loginForm').show();
    
    $('#loginForm  a').on('click', function(e){
        e.preventDefault();
        $('#registrationForm').show();
        $('#loginForm').hide();    
    });
    
    function isUserNameValid(userName)
    {
        userName = userName.trim();
        if((userName.length >= 5))
        {
            return true;
        }
        return false;
    }
    
    function isPasswordValid(password)
    {
        if (password.length < 6 || (password.length > 50) || (password.search(/\d/) == -1) || (password.search(/[a-zA-Z]/) == -1) || (password.search(/[^a-zA-Z0-9\!\@\#\$\%\^\&\*\(\)\_\+]/) != -1)) 
        {
            return false;
        }
        return true;
    }
    
    function loadActivities()
    {
        $('#mainBody').hide();
        $('#activityBody').show();
        var postData = "";
        //alert($.cookie('id'));
        $('td').addClass("{ color : black;}");
        $('#activitiesSection').jtable({
            title: 'Activity List',
            paging: true,
            pageSize: 10,
            sorting: true,
            multiSorting: true,
            defaultSorting: 'title',
            actions: {
                listAction : function (postData, jtParams) {
                    console.log("Loading from custom function...");
                    $('td').addClass("{ color: black; }");
                    return $.Deferred(function ($dfd) {
                        $.ajax({
                            url: 'todo/activities',
                            type: 'GET',
                            data: '',
                            dataType: 'json',
                            success: function (data) {
                                var tabledata = {};
                                tabledata["Result"] = "OK";
                                tabledata["Records"] = data;
                                tabledata["TotalRecordCount"] = data.length;
                                $dfd.resolve(tabledata);
                            },
                            error: function () {
                                $dfd.reject();
                            }
                        });
                    });
                },
                
                createAction: function (postData) {
                    console.log("creating from custom function...");
                    
                    return $.Deferred(function ($dfd) {
                        $.ajax({
                            url: 'todo/activities',
                            type: 'POST',
                            dataType: 'json',
                            data: postData,
                            success: function (data) {
                                var returndata = {};
                                returndata["Result"] = "OK";
                                returndata["Record"] = data;
                                $dfd.resolve(returndata);
                            },
                            error: function () {
                                $dfd.reject();
                            }
                        });
                    });
                },
                
                updateAction: function(postData) {
                    var activityId = postData.split('&')[0].split('=')[1];
                   
                    return $.Deferred(function ($dfd) {
                        $.ajax({
                            url: 'todo/activities/' + activityId,
                            type: 'PUT',
                            dataType: 'json',
                            data: postData,
                            success: function (data) {
                                var returndata = {};
                                returndata["Result"] = "OK";
                                returndata["Record"] = data;
                                $dfd.resolve(returndata)
                            },
                            error: function () {
                                $dfd.reject();
                            }
                        });
                    });
                },
                
                deleteAction: function(postData) {
                                       
                    return $.Deferred(function ($dfd) {
                        $.ajax({
                            url: 'todo/activities/' + postData.id,
                            type: 'DELETE',
                            dataType: 'json',
                            data: postData,
                            success: function (data) {
                                var returndata = {};
                                returndata["Result"] = "OK";
                                $dfd.resolve(returndata)
                            },
                            error: function () {
                                $dfd.reject();
                            }
                        });
                    });
                }
                
            },
            fields: {
                id: {
                    key: true,
                    create: false,
                    edit: false,
                    list: false
                },
                title: {
                    title: 'title',
                    width: '35%',
                    edit: false
                },
                description: {
                    title: 'description',
                    width: '50%'
                },
                ownerId: {
                    create: false,
                    edit: false,
                    list: false
                },
                done: {
                    title: 'Status',
                    width: '15%',
                    type: 'checkbox',
                    create : false,
                    values: { 'false': 'Pending', 'true': 'Done' }
                }
                
            }});
        
        
    }
    
    $('#loginSubmit').on('click', function(e){
        e.preventDefault();
        $('.errorMessages').remove();
        var userName = $('#loginName').val();
        var password = $('#loginPassword').val();
        var errorFields = "";
        if(!isUserNameValid(userName))
        {
            errorFields += "Username must be atleast 5 characters long <br>";
        }
        if(!isPasswordValid(password))
        {
            errorFields += "Password should be between 6 to 20 characters containing at least one uppercase, one lowercase and one among @,#,$,% <br>";
        }
        var postData = 'username=' + userName + '&password=' + password; 
        if(errorFields.length == 0)
        {
            $('.errorMessages').remove();
            var data = "";
            /* $.ajax({
                type: "POST",
                url: "http://localhost:8080/todo-service/todo/user",
                data: ,
                success: 
            }); */
            /*$.post("http://localhost:8080/todo-service/todo/user", "username=" + userName + "&password=" + password,
                   function(data){
                    alert(data);
                }); */
            $.ajax({
                url  : 'todo/user',
                type : "POST",
                data : postData
                }).done(function(data, statusText, xhr){
                            var status = xhr.status;                //200
                            var head = xhr.getAllResponseHeaders(); //Detail header info
                          
                            //alert("status = " + status + " head = "+ head); 
                            if (status == 200)
                            {
                                loadActivities();
                            }
                            else
                            {
                                errorFields = data;
                                errorFields = '<div class="errorMessages">' + errorFields + '</div>';
                                $(errorFields).appendTo('#registrationForm');                                
                            }
                                
                });
                   
            //$.post(,
        }
        else{
            errorFields = '<div class="errorMessages">' + errorFields + '</div>';
            $(errorFields).appendTo('#registrationForm');
        }
            
    });
    
    $('#registerSubmit').on('click', function(e){
        $('.errorMessages').remove();
        e.preventDefault();
        var userName = $('#newLoginName').val();
        var password = $('#newLoginPassword').val();
        var rePassword = $('#newLoginRePassword').val();
        var phoneNumber =  $('#newLoginPhoneNumber').val();
        var errorFields = "";
        if(!isUserNameValid(userName))
        {
            errorFields += "<p> Username must be atleast 5 characters long <br>";
        }
        if(!isPasswordValid(password))
        {
            errorFields += "Password should be between 6 to 20 characters containing at least one uppercase, one lowercase and one among @,#,$,% <br>";
        }
        if(password != rePassword)
        {
            errorFields += "Reenter the same password <p>";
        }
        if(phoneNumber.length != 10 && phoneNumber.search('\\d{10}') != 0)
        {
            errorFields += " Enter a valid phone number </p>";
        }
        //alert(errorFields);
        
        var postData = "username=" + userName + "&password=" + password + "&phone=" + phoneNumber;
        if(errorFields.length == 0)
        {
           $.ajax({
                url  : 'todo/user/register',
                type : "POST",
                data : postData
                }).done(function(data, statusText, xhr){
                            var status = xhr.status;                //200
                            var head = xhr.getAllResponseHeaders(); //Detail header info
                          
                            //alert("status = " + status + " head = "+ head); 
                            if (status == 201)
                            {
                                loadActivities();
                            }
                            else
                            {
                                errorFields = data;
                                errorFields = '<div class="errorMessages">' + errorFields + '</div>';
                                $(errorFields).appendTo('#registrationForm');                                
                            }
                                
                });             
        }
        else{
            errorFields = '<div class="errorMessages">' + errorFields + '</div>';
            $(errorFields).appendTo('#registrationForm');
        }
        
            
    });        
            
            
    $('#registrationForm  a').on('click', function(e){
        e.preventDefault();
        $('#registrationForm').hide();
        $('#loginForm').show();    
    });
    
});