<%-- Document : qr_code.jsp Created on : Nov 4, 2023, 2:39:52 PM Author : vnitd --%> <%@page contentType="text/html"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="https://unpkg.com/qr-code-styling@1.5.0/lib/qr-code-styling.js"></script>
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			html,
			body {
				width: 300px;
				height: 300px;
			}
		</style>
	</head>
	<body>
		<div id="canvas"></div>
		<script type="text/javascript">

			const qrCode = new QRCodeStyling({
			    "width": 300,
			    "height": 300,
			    "data": "https://next-team-client.vercel.app/dashboard/attendances/<% out.print(request.getParameter("code"));%>",
			    "margin": 0,
			    "qrOptions": {"typeNumber": "0", "mode": "Byte", "errorCorrectionLevel": "Q"},
			    "imageOptions": {"hideBackgroundDots": true, "imageSize": 0.4, "margin": 0},
			    "dotsOptions": {
			        "type": "extra-rounded",
			        "color": "#6a1a4c",
			        "gradient": {
			            "type": "linear",
			            "rotation": 0.7853981633974483,
			            "colorStops": [
			                {"offset": 0, "color": "#ff6200"},
			                {"offset": 1, "color": "#ffa770"}
			            ]
			        }
			    },
			    "backgroundOptions": {"color": "#ffffff"},
			    "image": "data:image/png;base64,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",
			    "dotsOptionsHelper": {
			        "colorType": {"single": true, "gradient": false},
			        "gradient": {"linear": true, "radial": false, "color1": "#6a1a4c", "color2": "#6a1a4c", "rotation": "0"}
			    },
			    "cornersSquareOptions": {
			        "type": "dot",
			        "color": "#000000"
			    },
			    "cornersSquareOptionsHelper": {
			        "colorType": {"single": true, "gradient": false},
			        "gradient": {"linear": true, "radial": false, "color1": "#000000", "color2": "#000000", "rotation": "0"}
			    },
			    "cornersDotOptions": {"type": "dot", "color": "#000000", "gradient": null},
			    "cornersDotOptionsHelper": {
			        "colorType": {"single": true, "gradient": false},
			        "gradient": {"linear": true, "radial": false, "color1": "#000000", "color2": "#000000", "rotation": "0"}
			    },
			    "backgroundOptionsHelper": {
			        "colorType": {"single": true, "gradient": false},
			        "gradient": {"linear": true, "radial": false, "color1": "#ffffff", "color2": "#ffffff", "rotation": "0"}
			    }
			}
			);

			qrCode.append(document.getElementById("canvas"));
		</script>
	</body>
</html>
