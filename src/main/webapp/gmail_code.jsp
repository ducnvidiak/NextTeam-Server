<%-- Document : gmail_code Created on : Sep 23, 2023, 12:48:57 AM Author : vnitd --%> <%@page contentType="text/html"
                                                                                              pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <style>
            * {
                margin: 0;
                padding: 0;
            }
            .body {
                background-color: #eee;
                font-family: Arial, Helvetica, sans-serif;
                padding-top: 20px;
            }
            .container {
                max-width: 600px;
                margin: 50px auto;
                padding: 50px 0;
                padding-bottom: 10px;
                background-color: white;
                border-radius: 12.5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            }
            h1 {
                color: #ed6a1a;
                border-left: 4px solid #ed6a1a;
                padding-left: 30px;
                font-size: 30px;
            }
            .inside-content {
                padding: 34px;
                padding-bottom: 25px;
            }
            .body1 {
                font-size: 20px;
                font-weight: bold;
                color: #555;
                margin-bottom: 10px;
            }
            .body2 {
                font-size: 17px;
                color: #666;
                margin-bottom: 15px;
                line-height: 1.5;
            }
            ul {
                margin-left: 30px;
                margin-bottom: 15px;
                color: #ed6a1a;
            }
            li {
                padding: 3px 0 3px 10px;
            }
            .code-block {
                background-color: #ffd7b588;
                padding: 12px 0;
                color: #ed6a1a;
                text-align: center;
                font-weight: bold;
                font-size: 25px;
                letter-spacing: 1.5px;
                margin: 25px 0;
                border-radius: 5px;
            }
            a.link {
                text-decoration: none;
                color: #ed6a1a;
            }
            .footer {
                color: #888;
                padding: 20px;
            }
            .footer p {
                font-size: 12px;
                text-align: center;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <div class="body">
            <div class="container">
                <h1>Yêu cầu xác minh</h1>
                <div class="inside-content">
                    <p class="body1">Vui lòng xác thực yêu cầu Khôi phục mật khẩu của bạn</p>
                    <p class="body2">Bạn đã gửi yêu cầu Khôi phục mật khẩu của một tài khoản:</p>
                    <ul>
                        <li><span class="body2">Tên người dùng: [USERNAME]</span></li>
                        <li><span class="body2">Mã số: [ID]</span></li>
                        <li><span class="body2">Vào lúc: [WHEN]</span></li>
                    </ul>
                    <p class="body2">
                        Để chắc chắn rằng bạn muốn khôi phục mật khẩu, hãy dùng mã sau đây (6 số) để tiếp tục với điều đó —
                        mã này sẽ hết hạn sau 10 phút.
                    </p>
                    <div class="code-block">[CODE]</div>
                    <p class="body1">Đấy không phải tôi!</p>
                    <p class="body2">
                        Nếu bạn không phải là người gửi yêu cầu này cho chúng tôi, vui lòng bỏ qua email này. Chúng tôi xin
                        lỗi đã làm phiền bạn. Chúc bạn một ngày vui vẻ!
                    </p>
                    <p class="body1">Làm sao để tôi có thể biết email này đến từ NextTeam?</p>
                    <p class="body2">
                        Chúng tôi là NextTeam, một nhóm đến từ Trường Đại học FPT. Vậy nên chúng tôi chỉ có duy nhất một
                        email để gửi thông báo cho bạn là
                        <a class="link" href="mailto:nextteam.fpt@gmail.com">nextteam.fpt@gmail.com</a>. Để tránh bị lừa đảo
                        bởi những email giả, xin vui lòng xem kĩ địa chỉ email gửi đến.
                    </p>
                </div>
            </div>
            <div class="footer">
                <p>Trường Đại học FPT phân hiệu Đà Nẵng, Khu Công nghệ cao, Ngũ Hành Sơn, Đà Nẵng, Việt Nam</p>
                <p>&copy; 2023 NextTeam</p>
            </div>
        </div>
    </body>
</html>
