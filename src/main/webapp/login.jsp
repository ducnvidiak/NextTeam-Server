<%-- 
    Document   : index.jsp
    Created on : 1 thg 7, 2023, 09:01:57
    Author     : phangiabao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset='utf-8'>
        <meta http-equiv='X-UA-Compatible' content='IE=edge'>
        <title>Login | Dormitory Management</title>
        <meta name='viewport' content='width=device-width, initial-scale=1'>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <link rel="stylesheet" href="css/admin-css.css">
    </head>

    <body>
        <%

            String error = request.getAttribute("error") + "";
            if (error.equals("null")) {
                error = "Sign in with Google is only available with Student Email. Not for staff!";
            }
        %>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <img class="text-center img-fluid" src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/FPT_Education_logo.svg/2560px-FPT_Education_logo.svg.png" alt="">
                            <div class="alert alert-danger" role="alert">
                                <%=error%>
                            </div>
                            <form action="login" method="post">
                                <input type="hidden" name="action" value="login"/>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="floatingInput" placeholder="Username" name="username">
                                    <label for="floatingInput">Username</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
                                    <label for="floatingPassword">Password</label>
                                </div>
                                <div class="d-grid">
                                    <button class="btn btn-primary btn-login text-uppercase fw-bold" name="login" type="submit">Sign
                                        in</button>
                                    
                                </div>
                                <div class="row mt-2">
                                    <div class="col-md-12">
                                        <a type="button" class="btn btn-warning" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/AssignmentPRJ301/login-google&response_type=code&client_id=1070962791948-v31k9v3iqe31r9b9ad5m3chg7hv28i3i.apps.googleusercontent.com&approval_prompt=force">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                                            <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"></path>
                                            </svg>
                                            Sign in with Email FPT
                                        </a>
                                        
                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

    </body>
    
<script>
    // Configs
    let liveChatBaseUrl   = document.location.protocol + '//' + 'livechat.fpt.ai/v36/src'
    let LiveChatSocketUrl = 'livechat.fpt.ai:443'
    let FptAppCode        = 'b959102a7ed00f933126885937774d50'
    let FptAppName        = 'Live support'
    // Define custom styles
    let CustomStyles = {
        // header
        headerBackground: 'linear-gradient(86.7deg, #C459F4FF 0.85%, #7E1CAAFF 98.94%)',
        headerTextColor: '#ffffffff',
        headerLogoEnable: true,
        headerLogoLink: 'https://chatbot-tools.fpt.ai/livechat-builder/img/theme/bank/logo.svg',
        headerText: 'Live support',
        // main
        primaryColor: '#9B34C9FF',
        secondaryColor: '#D8D8D8FF',
        primaryTextColor: '#ffffffff',
        secondaryTextColor: '#1F1F1FFF',
        buttonColor: '#9B34C9B3',
        buttonTextColor: '#ffffffff',
        bodyBackgroundEnable: true,
        bodyBackgroundLink: 'https://chatbot-tools.fpt.ai/livechat-builder/img/theme/bank/body.png',
        avatarBot: 'https://chatbot-tools.fpt.ai/livechat-builder/img/theme/bank/bot.svg',
        sendMessagePlaceholder: 'Enter your message here',
        // float button
        floatButtonLogo: 'https://chatbot-tools.fpt.ai/livechat-builder/img/theme/bank/coin.svg',
        floatButtonTooltip: 'Can I help you?',
        floatButtonTooltipEnable: true,
        // start screen
        customerLogo: 'https://chatbot-tools.fpt.ai/livechat-builder/img/theme/bank/logo.svg',
        customerWelcomeText: 'Please input your name',
        customerButtonText: 'Start',
        prefixEnable: false,
        prefixType: 'radio',
        prefixOptions: ["Anh","Chị"],
        prefixPlaceholder: 'Danh xưng',
        // custom css
        css: ''
    }
    // Get bot code from url if FptAppCode is empty
    if (!FptAppCode) {
        let appCodeFromHash = window.location.hash.substr(1)
        if (appCodeFromHash.length === 32) {
            FptAppCode = appCodeFromHash
        }
    }
    // Set Configs
    let FptLiveChatConfigs = {
        appName: FptAppName,
        appCode: FptAppCode,
        themes : '',
        styles : CustomStyles
    }
    // Append Script
    let FptLiveChatScript  = document.createElement('script')
    FptLiveChatScript.id   = 'fpt_ai_livechat_script'
    FptLiveChatScript.src  = liveChatBaseUrl + '/static/fptai-livechat.js'
    document.body.appendChild(FptLiveChatScript)
    // Append Stylesheet
    let FptLiveChatStyles  = document.createElement('link')
    FptLiveChatStyles.id   = 'fpt_ai_livechat_script'
    FptLiveChatStyles.rel  = 'stylesheet'
    FptLiveChatStyles.href = liveChatBaseUrl + '/static/fptai-livechat.css'
    document.body.appendChild(FptLiveChatStyles)
    // Init
    FptLiveChatScript.onload = function () {
        fpt_ai_render_chatbox(FptLiveChatConfigs, liveChatBaseUrl, LiveChatSocketUrl)
    }
</script>

</html>
