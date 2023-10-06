USE NextTeam
GO
/*
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
<<<<<<<<<< BEGIN: RESET DATABASE <<<<<<<<<<
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/
	DECLARE @Sql NVARCHAR(500) DECLARE @Cursor CURSOR

	SET @Cursor = CURSOR FAST_FORWARD FOR
	SELECT DISTINCT sql = 'ALTER TABLE [' + tc2.TABLE_SCHEMA + '].[' +  tc2.TABLE_NAME + '] DROP [' + rc1.CONSTRAINT_NAME + '];'
	FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS rc1
	LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc2 ON tc2.CONSTRAINT_NAME =rc1.CONSTRAINT_NAME

	OPEN @Cursor FETCH NEXT FROM @Cursor INTO @Sql

	WHILE (@@FETCH_STATUS = 0)
	BEGIN
	Exec sp_executesql @Sql
	FETCH NEXT FROM @Cursor INTO @Sql
	END

	CLOSE @Cursor DEALLOCATE @Cursor
	GO
	EXEC sp_MSforeachtable 'DROP TABLE ?'
/*
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>> END: RESET DATABASE >>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
*/
GO
/*
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
<<<<<<<<<< BEGIN: TẠO BẢNG <<<<<<<<<<
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/
	CREATE TABLE majors (
		id   INT NOT NULL IDENTITY(1, 1),
		name NVARCHAR(255) NOT NULL,

		PRIMARY KEY (id)
	);

	CREATE TABLE homeTowns (
		id      INT NOT NULL IDENTITY(1, 1),
		country NVARCHAR(56),
		city    NVARCHAR(128)

		PRIMARY KEY (id)
	);

	CREATE TABLE users (
		id           INT NOT NULL IDENTITY(1, 1),
		email        VARCHAR(255) NOT NULL,
		username     VARCHAR(30) NOT NULL,
		password     VARCHAR(60) NOT NULL,
		avatarUrl    VARCHAR(MAX),
		bannerUrl    VARCHAR(MAX),
		firstname    NVARCHAR(50),
		lastname     NVARCHAR(50),
		/*studentCode  VARCHAR(20) NOT NULL,*/ /*remove 3*/
		phoneNumber  VARCHAR(15) NOT NULL,
		major        INT,
		academicYear INT,
		gender       VARCHAR(10) NOT NULL,
		dob          DATE,
		homeTown     INT,
		facebookUrl  VARCHAR(MAX),
		linkedInUrl  VARCHAR(MAX),
		createdAt    DATETIME DEFAULT(GETDATE()),
		updatedAt    DATETIME DEFAULT(GETDATE()),
		isActive     BIT DEFAULT(1), /*ADD3*/
		isAdmin      BIT DEFAULT(0),

		PRIMARY KEY (id),
		FOREIGN KEY (major)    REFERENCES majors(id),
		FOREIGN KEY (homeTown) REFERENCES homeTowns(id)
	);

	CREATE TABLE clubCategories(
		id INT NOT NULL IDENTITY(1, 1),
		name NVARCHAR(100) NOT NULL,

		PRIMARY KEY (id)
	)

	CREATE TABLE clubs(
		id            INT NOT NULL IDENTITY(1, 1),
		name          NVARCHAR(100) NOT NULL,
		subname       VARCHAR(20) NOT NULL, /* add 2 */
		categoryId    INT NOT NULL,
		description   NTEXT,
		avatarUrl     VARCHAR(MAX),
		bannerUrl     VARCHAR(MAX),
		movementPoint INT DEFAULT(0),
		balance       REAL DEFAULT(0), /* add */
		createdAt     DATETIME DEFAULT(GETDATE()),
		updatedAt     DATETIME DEFAULT(GETDATE()),
		isActive      BIT DEFAULT(1), /*ADD3*/

		PRIMARY KEY (id),
		FOREIGN KEY (categoryId) REFERENCES clubCategories(id)
	);



	CREATE TABLE departments (
		id     INT NOT NULL IDENTITY(1, 1),
		clubId INT,
		name   NVARCHAR(50),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id)
	);

	CREATE TABLE roles (
		id   INT NOT NULL IDENTITY(1, 1),
		name NVARCHAR(50),

		PRIMARY KEY (id)
	);

	CREATE TABLE engagements (
		id           INT IDENTITY(1, 1) NOT NULL,
		userId       INT NOT NULL,
		departmentId INT NOT NULL,
		clubId		 INT NOT NULL,
		roleId       INT,
		cvUrl        VARCHAR(MAX) NOT NULL, /*add*/
		status 	     INT DEFAULT(0),
		createdAt    DATETIME DEFAULT(GETDATE()),
		updatedAt    DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (userId)       REFERENCES users(id),
		FOREIGN KEY (departmentId) REFERENCES departments(id),
		FOREIGN KEY (clubId)       REFERENCES clubs(id),
		FOREIGN KEY (roleId)       REFERENCES roles(id)
	);

	CREATE TABLE entranceInterviews (
		id           INT NOT NULL IDENTITY(1, 1),
		startTime    DATETIME NOT NULL,
		endTime      DATETIME NOT NULL,
		engagementId INT NOT NULL,
		comment      NTEXT,
		isApproved   BIT DEFAULT(NULL),
		approvedBy   INT, /*add*/
		createdAt    DATETIME DEFAULT(GETDATE()),
		updatedAt    DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (engagementId) REFERENCES engagements(id),
		FOREIGN KEY (approvedBy)   REFERENCES users(id)
	);

	CREATE TABLE locations (
		id          INT NOT NULL IDENTITY(1, 1),
		name        NVARCHAR(64),
		description NTEXT,

		PRIMARY KEY (id)
	)

	CREATE TABLE events (
		id           INT NOT NULL IDENTITY(1, 1),
		name         NVARCHAR(128) NOT NULL,
		description  NTEXT,
		registeredBy INT NOT NULL,
		locationId   INT NOT NULL,
		checkinCode  VARCHAR(255),
		startTime    DATETIME NOT NULL,
		endTime      DATETIME NOT NULL,
		type         VARCHAR(255),
		planUrl      VARCHAR(MAX), /*add*/
		bannerUrl	 VARCHAR(MAX),  /* add 2 */
		isApproved   BIT DEFAULT(NULL),
		response     NTEXT,
		clubId       INT NOT NULL,
		createdAt    DATETIME DEFAULT(GETDATE()),
		updatedAt    DATETIME DEFAULT(GETDATE())

		PRIMARY KEY (id),
		FOREIGN KEY (registeredBy) REFERENCES users(id),
		FOREIGN KEY (locationId)   REFERENCES locations(id),
		FOREIGN KEY (clubId)       REFERENCES clubs(id)
	);

	CREATE TABLE eventRegistrations(
		id                INT NOT NULL IDENTITY(1, 1), /*add*/
		event             INT NOT NULL,
		registeredBy      INT NOT NULL,
		isJoined          BIT DEFAULT(0),
		reasonsForAbsence NVARCHAR(255),
		createdAt         DATETIME DEFAULT(GETDATE()),
		updatedAt		  DATETIME DEFAULT(GETDATE())

		PRIMARY KEY (id),
		FOREIGN KEY (event)               REFERENCES  events(id),
		FOREIGN KEY (registeredBy)        REFERENCES  users(id),
	);

	CREATE TABLE eventVotations (
		id        INT NOT NULL IDENTITY(1, 1), /*add*/
		eventId   INT NOT NULL,
		userId    INT NOT NULL,
		isAgreed  BIT DEFAULT(NULL),
		createdAt DATETIME DEFAULT(GETDATE()),
		updatedAt DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (eventId) REFERENCES events(id),
		FOREIGN KEY (userId)  REFERENCES users(id),
	);

	CREATE TABLE plans (
		id          INT NOT NULL IDENTITY(1, 1),
		clubId      INT NOT NULL,
		title       NVARCHAR(255),
		content     NTEXT,
		attachement VARCHAR(255), /* URL to the file */
		response    NTEXT,
		isApproved  BIT DEFAULT(NULL), /* is approve by Admin */
		createdAt   DATETIME DEFAULT(GETDATE()),
		updatedAt   DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id)
	);

	CREATE TABLE privateNotifications (
		id        INT NOT NULL IDENTITY(1, 1),
		clubId    INT NOT NULL,
		sendTo    INT NOT NULL,
		hasSeen   BIT DEFAULT(0),
		seenTime  DATETIME,
		title     NVARCHAR(MAX) NOT NULL,
		content   NTEXT NOT NULL,
		createdAt DATETIME DEFAULT(GETDATE()),
		updatedAt DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id),
		FOREIGN KEY (sendTo) REFERENCES users(id)
	);

	CREATE TABLE publicNotifications (
		id        INT NOT NULL IDENTITY(1, 1),
		clubId    INT,
		title     NVARCHAR(MAX) NOT NULL,
		content   NTEXT NOT NULL,
		createdAt DATETIME DEFAULT(GETDATE()),
		updatedAt DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id),
	);

	CREATE TABLE publicNotificationViews (
		id                   INT NOT NULL IDENTITY(1, 1),
		publicNotificationId INT NOT NULL,
		hasSeenBy            INT NOT NULL,
		seenTime             DATETIME DEFAULT(GETDATE()), /* Đổi tên từ markTime */

		PRIMARY KEY (id),
		FOREIGN KEY (publicNotificationId)     REFERENCES  publicNotifications(id),
		FOREIGN KEY (hasSeenBy)                REFERENCES  users(id),
	);

	CREATE TABLE posts ( /* Đổi tên từ newsPosts */
		id        INT NOT NULL IDENTITY(1, 1),
		clubId    INT NOT NULL,
		imageUrl  VARCHAR(255),
		videoUrl  VARCHAR(255),
		title     NVARCHAR(128), /* Thêm cột Title */
		content   NTEXT,
		createdAt DATETIME DEFAULT(GETDATE()),  /*add*/
		updatedAt DATETIME DEFAULT(GETDATE()), /*add*/

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id)
	);

	CREATE TABLE postComments (
		id               INT NOT NULL,
		postId           INT NOT NULL,
		refPostCommentId INT,
		createdAt        DATETIME DEFAULT(GETDATE()), /*add*/
		updatedAt        DATETIME DEFAULT(GETDATE()), /*add*/

		PRIMARY KEY (id),
		FOREIGN KEY (postId) REFERENCES posts(id),
		FOREIGN KEY (refPostCommentId) REFERENCES postComments(id)
	);

	CREATE TABLE proposals (
		id         INT NOT NULL IDENTITY(1, 1),
		clubId     INT NOT NULL,
		title      NVARCHAR(128) NOT NULL,
		content    NTEXT NOT NULL,
		sendBy     INT NOT NULL,
		attach     VARCHAR(100),
		isApproved BIT DEFAULT(NULL),
		createdAt  DATETIME DEFAULT(GETDATE()),
		updatedAt  DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id),
		FOREIGN KEY (sendBy) REFERENCES users(id)
	);

	CREATE TABLE paymentCategories (
		id          INT NOT NULL IDENTITY(1, 1),
		title       NVARCHAR(50) NOT NULL,
		description NVARCHAR(255),
		clubId      INT NOT NULL,
		amount      REAL,
		createdAt   DATETIME DEFAULT(GETDATE()),
		updatedAt   DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id)
	);

	CREATE TABLE transactionHistories (
		id          INT NOT NULL IDENTITY(1, 1),
		paidBy      INT NOT NULL,
		/*clubId      INT NOT NULL,*/  /*remove*/
		categoryId  INT NOT NULL,
		status      NVARCHAR(50), /*ADD3*/
		paymentForm NVARCHAR(50), /*ADD3*/
		createdAt   DATETIME DEFAULT(GETDATE()),
		updatedAt   DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (paidBy) REFERENCES users(id),
		/* FOREIGN KEY (clubId) REFERENCES clubs(id), */
		FOREIGN KEY (categoryId) REFERENCES paymentCategories(id),
	);

	CREATE TABLE paymentExpenses (
		id          INT NOT NULL IDENTITY(1, 1),
		title       NVARCHAR(50) NOT NULL,
		description NVARCHAR(255),
		clubId      INT NOT NULL,
		amount      REAL,
		createdAt   DATETIME DEFAULT(GETDATE()),
		updatedAt   DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (clubId) REFERENCES clubs(id)
	);

	CREATE TABLE pointsHistories (
		id         INT NOT NULL IDENTITY(1, 1),
		createdBy  INT NOT NULL,
		receivedBy INT NOT NULL,
		clubId     INT NOT NULL,
		amount     INT NOT NULL,
		reason     VARCHAR (255),
		createdAt  DATETIME DEFAULT(GETDATE()),
		updatedAt  DATETIME DEFAULT(GETDATE()),

		PRIMARY KEY (id),
		FOREIGN KEY (createdBy) REFERENCES users(id),
		FOREIGN KEY (receivedBy) REFERENCES users(id),
		FOREIGN KEY (clubId) REFERENCES clubs(id),
	);

	CREATE TABLE otpCode (
		id        INT NOT NULL IDENTITY(1, 1),
		userId    INT NOT NULL,
		code      NVARCHAR(6) NOT NULL,
		type      NVARCHAR(50) NOT NULL,
		createdAt DATETIME NOT NULL DEFAULT(GETDATE()),
		expiredAt DATETIME NOT NULL,
		isDisable BIT NOT NULL DEFAULT(0),
		chance   INT NOT NULL DEFAULT(3),

		PRIMARY KEY (id),
		FOREIGN KEY (userId) REFERENCES users(id)
	)
/*
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>> END: TẠO BẢNG >>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
*/
GO
/*
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
<<<<<<<<<< BEGIN: TẠO TRIGGER <<<<<<<<<<
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/
	IF OBJECT_ID('TR_OneToOneRelationshipBetweenEntranceInterviewsAndEngagements', 'TR') IS NOT NULL
		DROP TRIGGER TR_OneToOneRelationshipBetweenEntranceInterviewsAndEngagements;
	GO
	CREATE TRIGGER TR_OneToOneRelationshipBetweenEntranceInterviewsAndEngagements
	ON entranceInterviews
	AFTER INSERT, UPDATE
	AS
	BEGIN
		BEGIN TRY
			BEGIN TRANSACTION
		
			IF EXISTS(
				SELECT engagementId
				FROM inserted
				GROUP BY engagementId
				HAVING COUNT(*) > 1
			)
			BEGIN
				ROLLBACK TRANSACTION;
				THROW 50001, 'One-to-one Relationship between entranceInterviews and engagements violated', 1;
			END;
			COMMIT TRANSACTION;
		END TRY
		BEGIN CATCH
			THROW;
		END CATCH
	END;
	GO
	IF OBJECT_ID('TR_UpdateUser', 'TR') IS NOT NULL /* for users */
		DROP TRIGGER TR_UpdateUser
	GO
	CREATE TRIGGER TR_UpdateUser
	ON users
	AFTER UPDATE
	AS
	BEGIN
		UPDATE users
		SET updatedAt = GETDATE()
		FROM users
		INNER JOIN inserted ON users.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateClub', 'TR') IS NOT NULL /* for clubs */
		DROP TRIGGER TR_UpdateClub
	GO
	CREATE TRIGGER TR_UpdateClub
	ON clubs
	AFTER UPDATE
	AS
	BEGIN
		UPDATE clubs
		SET updatedAt = GETDATE()
		FROM clubs
		INNER JOIN inserted ON clubs.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateEngagement', 'TR') IS NOT NULL /* for engagements */
		DROP TRIGGER TR_UpdateEngagement
	GO
	CREATE TRIGGER TR_UpdateEngagement
	ON engagements
	AFTER UPDATE
	AS
	BEGIN
		UPDATE engagements
		SET updatedAt = GETDATE()
		FROM engagements
		INNER JOIN inserted ON engagements.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateEntranceInterview', 'TR') IS NOT NULL /* for entranceInterviews */
		DROP TRIGGER TR_UpdateEntranceInterview
	GO
	CREATE TRIGGER TR_UpdateEntranceInterview
	ON entranceInterviews
	AFTER UPDATE
	AS
	BEGIN
		UPDATE entranceInterviews
		SET updatedAt = GETDATE()
		FROM entranceInterviews
		INNER JOIN inserted ON entranceInterviews.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateEvent', 'TR') IS NOT NULL /* for events */
		DROP TRIGGER TR_UpdateEvent
	GO
	CREATE TRIGGER TR_UpdateEvent
	ON events
	AFTER UPDATE
	AS
	BEGIN
		UPDATE events
		SET updatedAt = GETDATE()
		FROM events
		INNER JOIN inserted ON events.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateEventRegistration', 'TR') IS NOT NULL /* for eventRegistrations */
		DROP TRIGGER TR_UpdateEventRegistration
	GO
	CREATE TRIGGER TR_UpdateEventRegistration
	ON eventRegistrations
	AFTER UPDATE
	AS
	BEGIN
		UPDATE eventRegistrations
		SET updatedAt = GETDATE()
		FROM eventRegistrations
		INNER JOIN inserted ON eventRegistrations.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateEventVotation', 'TR') IS NOT NULL /* for eventVotations */
		DROP TRIGGER TR_UpdateEventVotation
	GO
	CREATE TRIGGER TR_UpdateEventVotation
	ON eventVotations
	AFTER UPDATE
	AS
	BEGIN
		UPDATE eventVotations
		SET updatedAt = GETDATE()
		FROM eventVotations
		INNER JOIN inserted ON eventVotations.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePlan', 'TR') IS NOT NULL /* for plans */
		DROP TRIGGER TR_UpdatePlan
	GO
	CREATE TRIGGER TR_UpdatePlan
	ON plans
	AFTER UPDATE
	AS
	BEGIN
		UPDATE plans
		SET updatedAt = GETDATE()
		FROM plans
		INNER JOIN inserted ON plans.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePrivateNotification', 'TR') IS NOT NULL /* for privateNotifications */
		DROP TRIGGER TR_UpdatePrivateNotification
	GO
	CREATE TRIGGER TR_UpdatePrivateNotification
	ON privateNotifications
	AFTER UPDATE
	AS
	BEGIN
		UPDATE privateNotifications
		SET updatedAt = GETDATE()
		FROM privateNotifications
		INNER JOIN inserted ON privateNotifications.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePublicNotification', 'TR') IS NOT NULL /* for publicNotifications */
		DROP TRIGGER TR_UpdatePublicNotification
	GO
	CREATE TRIGGER TR_UpdatePublicNotification
	ON publicNotifications
	AFTER UPDATE
	AS
	BEGIN
		UPDATE publicNotifications
		SET updatedAt = GETDATE()
		FROM publicNotifications
		INNER JOIN inserted ON publicNotifications.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePost', 'TR') IS NOT NULL /* for posts */
		DROP TRIGGER TR_UpdatePost
	GO
	CREATE TRIGGER TR_UpdatePost
	ON posts
	AFTER UPDATE
	AS
	BEGIN
		UPDATE posts
		SET updatedAt = GETDATE()
		FROM posts
		INNER JOIN inserted ON posts.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePostComment', 'TR') IS NOT NULL /* for postComments */
		DROP TRIGGER TR_UpdatePostComment
	GO
	CREATE TRIGGER TR_UpdatePostComment
	ON postComments
	AFTER UPDATE
	AS
	BEGIN
		UPDATE postComments
		SET updatedAt = GETDATE()
		FROM postComments
		INNER JOIN inserted ON postComments.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateProposal', 'TR') IS NOT NULL /* for proposals */
		DROP TRIGGER TR_UpdateProposal
	GO
	CREATE TRIGGER TR_UpdateProposal
	ON proposals
	AFTER UPDATE
	AS
	BEGIN
		UPDATE proposals
		SET updatedAt = GETDATE()
		FROM proposals
		INNER JOIN inserted ON proposals.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePaymentCategories', 'TR') IS NOT NULL /* for paymentCategories */
		DROP TRIGGER TR_UpdatePaymentCategories
	GO
	CREATE TRIGGER TR_UpdatePaymentCategories
	ON paymentCategories
	AFTER UPDATE
	AS
	BEGIN
		UPDATE paymentCategories
		SET updatedAt = GETDATE()
		FROM paymentCategories
		INNER JOIN inserted ON paymentCategories.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdateTransactionHistory', 'TR') IS NOT NULL /* for transactionHistories */
		DROP TRIGGER TR_UpdateTransactionHistory
	GO
	CREATE TRIGGER TR_UpdateTransactionHistory
	ON transactionHistories
	AFTER UPDATE
	AS
	BEGIN
		UPDATE transactionHistories
		SET updatedAt = GETDATE()
		FROM transactionHistories
		INNER JOIN inserted ON transactionHistories.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePaymentExpense', 'TR') IS NOT NULL /* for paymentExpenses */
		DROP TRIGGER TR_UpdatePaymentExpense
	GO
	CREATE TRIGGER TR_UpdatePaymentExpense
	ON paymentExpenses
	AFTER UPDATE
	AS
	BEGIN
		UPDATE paymentExpenses
		SET updatedAt = GETDATE()
		FROM paymentExpenses
		INNER JOIN inserted ON paymentExpenses.id = inserted.id;
	END;
	GO
	IF OBJECT_ID('TR_UpdatePointsHistory', 'TR') IS NOT NULL /* for pointsHistories */
		DROP TRIGGER TR_UpdatePointsHistoryy
	GO
	CREATE TRIGGER TR_UpdatePointsHistory
	ON pointsHistories
	AFTER UPDATE
	AS
	BEGIN
		UPDATE pointsHistories
		SET updatedAt = GETDATE()
		FROM pointsHistories
		INNER JOIN inserted ON pointsHistories.id = inserted.id;
	END;
/*
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>> END: TẠO TRIGGER >>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
*/
GO
/*
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
<<<<<<<<<< BEGIN: TẠO FUNCTION <<<<<<<<<<
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/
	IF OBJECT_ID(N'P_otpLostChance', N'P') IS NOT NULL
    	DROP PROCEDURE P_otpLostChance;
	GO

	CREATE PROCEDURE P_otpLostChance
		@id INT,
		@returnValue INT OUTPUT
	AS
	BEGIN
		DECLARE @chance INT;
		SELECT @chance = chance FROM otpCode WHERE id = @id;
		SET @chance = @chance - 1;
		IF @chance = 0
			UPDATE otpCode SET isDisable = 1 WHERE id = @id;
		ELSE
			UPDATE otpCode SET chance = @chance WHERE id = @id;
		SET @returnValue = @chance;
	END;
/*
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>> END: TẠO FUNCTION >>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
*/
GO
/*
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
<<<<<<<<<< BEGIN: DỮ LIỆU MẪU <<<<<<<<<<
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/

	INSERT INTO homeTowns (country, city)
	VALUES (N'Việt Nam', N'Hà Nội'),
	       (N'Việt Nam', N'Hồ Chí Minh'),
	       (N'Việt Nam', N'Đà Nẵng'),
	       (N'Việt Nam', N'Hải Phòng'),
	       (N'Việt Nam', N'Cần Thơ');

	INSERT INTO clubCategories(name)
	VALUES (N'Học thuật'),
		   (N'Cộng đồng'),
		   (N'Thể thao'),
		   (N'Năng khiếu')

	INSERT INTO clubs (name, subname, categoryId, description, movementPoint, balance)
	VALUES (N'Câu lạc bộ lập trình', 'FU-DEVER', 1, N'Câu lạc bộ lập trình được thành lập với mục tiêu chia sẻ kiến thức, tạo ra môi trường học tập và làm việc cùng nhau trong lĩnh vực lập trình và phát triển phần mềm. Sứ mệnh của chúng tôi là khơi dậy niềm đam mê lập trình, hỗ trợ thành viên phát triển kỹ năng kỹ thuật, và thúc đẩy sáng tạo trong lĩnh vực công nghệ thông tin', 0, 0),
	       (N'Câu lạc bộ truyền thông', 'FUM', 2, N'Câu lạc bộ lập trình được thành lập với mục tiêu chia sẻ kiến thức, tạo ra môi trường học tập và làm việc cùng nhau trong lĩnh vực lập trình và phát triển phần mềm. Sứ mệnh của chúng tôi là khơi dậy niềm đam mê lập trình, hỗ trợ thành viên phát triển kỹ năng kỹ thuật, và thúc đẩy sáng tạo trong lĩnh vực công nghệ thông tin', 0, 0),
	       (N'Google Developer Student Clubs - FPT University Da Nang', 'GDSC', 4, N'Câu lạc bộ Google Developer Student Clubs - FPT University Đà Nẵng (GDSCTech Đà Nẵng) là một tổ chức sinh viên đam mê về công nghệ thông tin, phát triển ứng dụng, và các công nghệ liên quan. Sứ mệnh của chúng tôi là khơi dậy đam mê sáng tạo công nghệ trong cộng đồng sinh viên, tạo cơ hội học hỏi và phát triển kỹ năng kỹ thuật, và thúc đẩy sự kết nối với ngành công nghiệp công nghệ thông tin.', 0, 0),
	       (N'FUDA Chess Club', 'Vahalla', 3, N'Câu lạc bộ Valhalla - FUDA Chess Club là một tổ chức tại FUDA (tên trường hoặc tổ chức của bạn) dành riêng cho những người yêu thích cờ vua và muốn chia sẻ đam mê với những người khác. Sứ mệnh của câu lạc bộ là thúc đẩy sự phát triển cá nhân, sáng tạo, và kỹ năng trong lĩnh vực cờ vua, cung cấp môi trường thân thiện để gặp gỡ và thách thức trong trò chơi cờ vua, và xây dựng cộng đồng đam mê về cờ vua tại FUDA.', 0, 0);
	
	INSERT INTO locations (name, description)
	VALUES (N'Phòng 201', N'Mô tả cho Phòng 201'),
	       (N'Phòng 202', N'Mô tả cho Phòng 202'),
	       (N'Phòng 203', N'Mô tả cho Phòng 203'),
	       (N'Phòng 204', N'Mô tả cho Phòng 204'),
	       (N'Phòng 205', N'Mô tả cho Phòng 205'),
	       (N'Phòng 206', N'Mô tả cho Phòng 206'),
	       (N'Phòng 207', N'Mô tả cho Phòng 207'),
	       (N'Phòng 208', N'Mô tả cho Phòng 208'),
	       (N'Phòng 209', N'Mô tả cho Phòng 209'),
	       (N'Phòng 210', N'Mô tả cho Phòng 210'),
	       (N'Phòng 211', N'Mô tả cho Phòng 211'),
	       (N'Phòng 212', N'Mô tả cho Phòng 212'),
	       (N'Phòng 213', N'Mô tả cho Phòng 213'),
	       (N'Phòng 214', N'Mô tả cho Phòng 214'),
	       (N'Phòng 215', N'Mô tả cho Phòng 215'),
	       (N'Phòng 216', N'Mô tả cho Phòng 216'),
	       (N'Phòng 217', N'Mô tả cho Phòng 217'),
	       (N'Phòng 218', N'Mô tả cho Phòng 218'),
	       (N'Phòng 219', N'Mô tả cho Phòng 219'),
	       (N'Phòng 220', N'Mô tả cho Phòng 220');

	INSERT INTO majors (name)
	VALUES (N'Kỹ thuật phần mềm'),
	       (N'An toàn thông tin'),
	       (N'Trí tuệ nhân tạo'),
	       (N'Thiết kế đồ họa'),
	       (N'Kinh doanh quốc tế'),
	       (N'Ngôn ngữ Anh'),
	       (N'Ngôn ngữ Nhật'),
	       (N'Digital marketing'),
	       (N'Truyền thông đa phương tiện')

	INSERT INTO roles(name)
	VALUES ('member'),
		   ('manager')

	INSERT INTO users(email, username, password, avatarUrl, bannerUrl, firstname, lastname, phoneNumber, major, academicYear, gender, dob, homeTown, isAdmin)
	VALUES ('thangtvb.dev@gmail.com', 'DE170145', '$2a$10$0QVDV9mai3TAhbYMqiAJlu8PbIuWRRKqPbsGS3kgS1QjeRDbowcGq', 'https://images.vexels.com/media/users/3/129616/isolated/preview/fb517f8913bd99cd48ef00facb4a67c0-businessman-avatar-silhouette-by-vexels.png', 'https://t4.ftcdn.net/jpg/04/95/28/65/360_F_495286577_rpsT2Shmr6g81hOhGXALhxWOfx1vOQBa.jpg', N'Trần Văn', N'Bảo Thắng', '0828828497', 1, 2021, 'Male', '2023-12-19', 1, 0),
           ('tranvietdangquang@gmail.com', 'DE170014', '$2a$10$QEsErWOOKq8RSo30NfqRDurENcgx4UnMdExhsrMMvzvmd956zoVAq', 'https://images.vexels.com/media/users/3/129616/isolated/preview/fb517f8913bd99cd48ef00facb4a67c0-businessman-avatar-silhouette-by-vexels.png', 'https://t4.ftcdn.net/jpg/04/95/28/65/360_F_495286577_rpsT2Shmr6g81hOhGXALhxWOfx1vOQBa.jpg', N'Trần Việt', N'Đăng Quang', '0866191103', 1, 2021, 'Male', '2023-11-19', 1, 1)

	INSERT INTO publicNotifications(clubId, title, content)
	VALUES (1, N'FUDN [FPTU.DN-DVSV] - V/v triển khai đăng ký học bằng lái xe kỳ Fall 2023 (Đợt 1)', N'PHAN GIA BẢO'),
		   (1, N'FUDN [Khảo thí] Danh sách phòng thi Retake môn SSL101c ngày 23/09/2023 (đợt 2 kỳ Summer 2023)', N'<p style=\"color: rgb(51, 51, 51);\">Phòng Khảo thí thông báo đến các em sinh viên&nbsp;Danh sách phòng thi ngày 23/09/2023. Chi tiết ở tệp đính kèm.&nbsp;</p><p style=\"color: rgb(51, 51, 51);\">Hình thức&nbsp;thi&nbsp;tập trung tại trường.</p><p style=\"color: rgb(51, 51, 51);\">Khi đi&nbsp;thi&nbsp;các em sinh viên chuẩn bị thẻ sinh viên, bút, tai nghe có dây và dây sạc laptop; kiểm tra phần mềm và máy tính cá nhân trước ngày&nbsp;thi; đọc kỹ&nbsp;nội&nbsp;quy phòng&nbsp;thi&nbsp;tại&nbsp;<a href=\"https://fap.fpt.edu.vn/CmsFAP/NewsDetail.aspx?id=24797\" target=\"_blank\" title=\"https://drive.google.com/file/d/1XhFfCkB5cWVAhHfQnx76vYj5gGi6OSWA/view\">đây</a>.</p><p style=\"color: rgb(51, 51, 51);\"><span style=\"color: rgb(255, 0, 0);\"><span style=\"font-weight: 700;\">Sinh viên lưu ý:</span>&nbsp;Thời hạn nhận thắc mắc điểm Bonus môn SSL101c:&nbsp;<span style=\"font-weight: 700;\">từ ngày công bố điểm Final đến hết ngày thi Retake</span>. Sau thời gian trên, phòng Khảo thí sẽ không tiếp nhận và xử lý các thắc mắc liên quan đến điểm bonus.</span></p><table border=\"1\" cellspacing=\"0\" style=\"font-size: 13px; background-color: rgb(255, 255, 255); width: 1140px;\"><tbody><tr style=\"border-bottom: 1px solid rgb(240, 240, 240);\"><td colspan=\"6\" style=\"border-left-width: 0px; border-right-width: 0px; vertical-align: top;\"><span style=\"font-weight: 700;\">Thời hạn đăng ký thi cải thiện:</span>&nbsp;từ ngày có kết quả đến&nbsp;<span style=\"font-weight: 700;\">trước ngày thi Retake 1/2 NGÀY LÀM VIỆC</span><br><span style=\"font-weight: 700;\">Kênh đăng ký:</span>&nbsp;http://fap.fpt.edu.vn/<br><span style=\"font-weight: 700;\">Điều kiện:&nbsp;</span>điểm thi Final đủ đạt môn<br><span style=\"font-weight: 700;\">Thời gian thi cải thiện điểm:</span>&nbsp;theo lịch thi Retake của môn đăng ký<br>Khi đơn đăng ký được chấp thuận, điểm thi Final bị hủy, điểm thi Retake là kết quả cuối cùng.<br><span style=\"font-weight: 700;\">Lưu ý:</span>&nbsp;Danh sách thi lại có thể không cập nhật trường hợp thi cải thiện điểm; Sinh viên đến tại Phòng 210 để biết phòng thi sau khi đơn Cải thiện điểm được duyệt.</td></tr></tbody></table><p style=\"color: rgb(51, 51, 51);\">Chúc các em thi tốt.</p><p style=\"color: rgb(51, 51, 51);\">&nbsp;</p><p style=\"color: rgb(51, 51, 51);\">Academic Examination&nbsp;Department would like to inform students about the exam room list for 23/09/2023. Details in the attached file.</p><p style=\"color: rgb(51, 51, 51);\">Students come to campus to take exam.</p><p style=\"color: rgb(51, 51, 51);\">Remember to bring&nbsp;ID card, pen, wired earphones and laptop charger with you; check the testing software and personal laptop before the exam date; read the Examination room regulations carefully&nbsp;<a href=\"https://fap.fpt.edu.vn/CmsFAP/NewsDetail.aspx?id=24797\" target=\"_blank\" title=\"https://drive.google.com/file/d/1XhFfCkB5cWVAhHfQnx76vYj5gGi6OSWA/view\">here</a>.</p><p style=\"color: rgb(51, 51, 51);\">Best regards</p><div><br></div>');

	INSERT INTO events (id, name, description, registeredBy, locationId, checkinCode, startTime, endTime, type, planUrl, bannerUrl, isApproved, response, clubId)
	VALUES (
		1,
		N'Zoom | FES-TECHSpeak #03 | CHANGE TO CHANCE - Công nghệ AI & Ứng dụng trong đồ họa sáng tạo',
		N'🎤 Host: Anh Lê Ngọc Tuấn - Giám đốc Trải nghiệm Công Nghệ, Ban Công tác học đường, Tổ chức giáo dục FPT  ​🗣️ Diễn giả:   ​Anh Vũ Hồng Chiên - Giám đốc Trung tâm Nghiên cứu và Ứng dụng Trí tuệ nhân tạo Quy Nhơn (QAI - FPT Software)  ​Anh Đặng Việt Hùng - Design Manager tại Gianty chi nhánh Đà Nẵng  ​Topic:  ​• Giải mã công nghệ “Generative AI\\\" và xu hướng ứng dụng trong các nghề nghiệp tương lai  • Nghề thiết kế đồ họa và ứng dụng công cụ AI trong thiết kế  • Thảo luận chủ đề AI có thay thế được chuyên gia đồ họa và thiết kế trong sáng tạo, xây dựng ứng dụng?',
		1,
		1,
		NULL,
		'2023-09-20T16:00:00',
		'2023-09-20T20:00:00',
		'public',
		NULL,
		'https://images.lumacdn.com/cdn-cgi/image/format=auto,fit=cover,dpr=2,quality=75,width=960,height=480/event-covers/w9/21154ed7-dc92-4c28-b582-9a5adb206fa7',
		NULL,
		NULL,
		1
	),
	(
		2,
		N'Zoom | FES-TECHSpeak #02 | BORN 2 BOND - Xây dựng và phát triển Câu lạc bộ',
		N'​FES-TECHSpeak #02 IN YOUR AREA  ​​“BORN 2 BOND - XÂY DỰNG VÀ PHÁT TRIỂN CÂU LẠC BỘ” 🚀  ​​💡 Bạn có phải là thành viên của câu lạc bộ Công nghệ và đang tìm kiếm lời khuyên về cách xây dựng một cộng đồng mạnh mẽ và sôi nổi? Bạn có muốn tìm hiểu cách tổ chức các hoạt động hấp dẫn để giữ chân các thành viên của mình và thu hút những người mới không? Đừng tìm kiếm đâu xa! 🚀  ​​Vì ngay tại FES-TECHSpeak #02 | BORN 2 BOND, bạn sẽ được lắng nghe những chia sẻ về cách xây dựng và phát triển câu lạc bộ hiệu quả và thảo luận cùng các diễn giả giàu kinh nghiệm trong lĩnh vực này.🎙️  ​​Cho dù bạn là thành viên, một người leader đầy tham vọng hay chỉ đơn giản là tò mò về sự phát triển của câu lạc bộ, thì buổi nói chuyện này là dành cho bạn! 🙌🏼  ​​Thông tin cụ thể:  ​​📅 Thời gian 09:00-10:30 Thứ Bảy, ngày 29/07/2023  ​​📍 Link Zoom tại đây  ​​🗣️ Diễn giả:  ​​Anh Lê Ngọc Tuấn: Giám đốc Trải nghiệm Công nghệ, Ban Công tác học đường, Tổ chức giáo dục FPT  ​​Chị Nguyễn Kim Chi: Cán bộ Phòng Hợp tác Quốc tế & Phát triển Cá nhân, Trường Đại học FPT Hà Nội  ​​Anh Võ Hoàng Sơn - Thực tập sinh lĩnh vực Mobile Development & Penetration Testing tại VNPT Cyber Immunity, Chủ nhiệm Câu lạc bộ Google Developer Student Clubs, Trường Đại học FPT Đà Nẵng',
		1,
		2,
		NULL,
		'2023-09-19T12:00:00',
		'2023-09-23T16:00:00',
		'public',
		NULL,
		'https://images.lumacdn.com/cdn-cgi/image/format=auto,fit=cover,dpr=2,quality=75,width=960,height=480/event-covers/n3/845d20e9-4aec-494c-a3fc-6014cf787ae1',
		NULL,
		NULL,
		2
	);


/*
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>> END: DỮ LIỆU MẪU >>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
*/
GO
