/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.16 : Database - mental_health2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mental_health2` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mental_health2`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `name` varchar(255) DEFAULT NULL COMMENT '管理员姓名',
  `gender` int(11) DEFAULT NULL COMMENT '管理员性别',
  `age` int(11) DEFAULT NULL COMMENT '管理员年龄',
  `tel` varchar(255) DEFAULT NULL COMMENT '管理员电话',
  `email` varchar(255) DEFAULT NULL COMMENT '管理员邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`gender`,`age`,`tel`,`email`,`password`) values 
(1,'Admin1',1,45,'13245671234','1234534@qq.com','123456');

/*Table structure for table `appointment` */

DROP TABLE IF EXISTS `appointment`;

CREATE TABLE `appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) DEFAULT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `dates` varchar(20) DEFAULT NULL,
  `times` varchar(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `cause` varchar(255) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `appointment` */

insert  into `appointment`(`id`,`stu_id`,`doctor_id`,`dates`,`times`,`content`,`state`,`cause`,`gmt_create`) values 
(3,12,1,'2021-04-14','8:00-10:00','测试预约功能test2',2,'测试审核不通过',1618228562645),
(4,12,4,'2021-04-14','16:00-18:00','学生测试预约功能test3',0,NULL,1618228835088),
(5,12,2,'2021-04-14','14:00-16:00','test',0,NULL,1618241863897),
(6,13,1,'2021-05-08','10:00-12:00','学生测试提问题功能test1',2,'撒旦法分为',1620355919636),
(7,12,2,'2021-05-14','10:00-12:00','由于这次期中考试成绩不理想，心里总是很压抑',1,NULL,1620832735958);

/*Table structure for table `ask_and_answer` */

DROP TABLE IF EXISTS `ask_and_answer`;

CREATE TABLE `ask_and_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题中间表id',
  `quest_id` int(11) DEFAULT NULL COMMENT '问题id',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生id',
  `doctor_id` int(11) DEFAULT NULL COMMENT '医生id',
  `answer` varchar(255) DEFAULT NULL COMMENT '答案',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `ask_and_answer` */

insert  into `ask_and_answer`(`id`,`quest_id`,`stu_id`,`doctor_id`,`answer`,`gmt_create`,`gmt_modified`) values 
(1,1,10,1,'答案test',1616854266855,1616916596532),
(3,2,10,NULL,NULL,NULL,NULL),
(4,4,11,1,'测试回答问题test1',NULL,1616921391536),
(7,1,10,1,'医师端测试回答问题功能',NULL,1616916594723),
(8,1,10,1,'测试医师回答问题插入创建时间',1616917311057,1616917311057),
(12,5,12,1,'测试回答啦啦啦同学问题',1616923253932,1616923420122),
(13,6,12,NULL,NULL,1616930997229,NULL),
(14,5,12,1,'测试同一个问题不同人回复的效果',1616935146369,1616935146369),
(15,7,12,1,'测试回答',1617937455986,1617937527801),
(16,8,13,NULL,NULL,1620355960840,NULL);

/*Table structure for table `chat_friends` */

DROP TABLE IF EXISTS `chat_friends`;

CREATE TABLE `chat_friends` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '聊天好友表id',
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `user_img` varchar(255) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `chat_friends` */

insert  into `chat_friends`(`id`,`user_id`,`friend_id`,`nick_name`,`user_img`,`gmt_create`,`gmt_modified`) values 
(6,12,1,NULL,NULL,NULL,NULL),
(7,1,12,NULL,NULL,NULL,NULL),
(8,12,2,NULL,NULL,NULL,NULL),
(9,2,12,NULL,NULL,NULL,NULL),
(10,13,1,NULL,NULL,NULL,NULL),
(11,1,13,NULL,NULL,NULL,NULL);

/*Table structure for table `chat_message` */

DROP TABLE IF EXISTS `chat_message`;

CREATE TABLE `chat_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '聊天记录表id',
  `send_user_id` int(11) DEFAULT NULL,
  `receive_user_id` int(11) DEFAULT NULL,
  `message_type` tinyint(1) DEFAULT NULL,
  `send_text` varchar(255) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `chat_message` */

insert  into `chat_message`(`id`,`send_user_id`,`receive_user_id`,`message_type`,`send_text`,`send_time`) values 
(1,1,10,1,'学生侧聊天测试','2021-03-25 00:00:00'),
(2,11,1,1,'聊天页面测试2','2021-03-24 00:00:00'),
(3,10,1,1,'医师侧聊天测试','2021-03-24 00:00:00'),
(4,1,11,0,'<p>医师发消息框测试</p><p><br></p>','2021-03-25 00:00:00'),
(5,1,10,0,'医师聊天框测试','2021-03-25 00:00:00'),
(6,1,11,0,'<img src=\"http://localhost:8080/mhealth/assets/chat/layui/images/face/1.gif\" alt=\"[嘻嘻]\">','2021-03-25 00:00:00'),
(7,1,11,0,'时间标签测试','2021-03-25 00:00:00'),
(8,1,11,0,'时间标签测试test2','2021-03-25 09:12:08'),
(9,1,11,0,'<img src=\"http://localhost:8080/mhealth/assets/chat/layui/images/face/2.gif\" alt=\"[哈哈]\">','2021-03-25 09:12:57'),
(10,1,12,0,'测试在线聊天','2021-03-28 09:25:41'),
(11,12,1,0,'<img src=\"http://localhost:8080/mhealth/assets/chat/layui/images/face/1.gif\" alt=\"[嘻嘻]\">','2021-03-29 07:16:27'),
(12,1,12,0,'<p>第二次测试聊天功能</p>','2021-04-01 15:11:58'),
(13,12,1,0,'嘿嘿嘿','2021-04-12 15:41:14'),
(14,13,1,0,'<img src=\"http://localhost:8080/mhealth/assets/chat/layui/images/face/1.gif\" alt=\"[嘻嘻]\">','2021-05-07 02:56:03'),
(15,13,1,0,'<img src=\"http://localhost:8080/mhealth/assets/chat/layui/images/face/2.gif\" alt=\"[哈哈]\">','2021-05-07 02:56:35');

/*Table structure for table `dates` */

DROP TABLE IF EXISTS `dates`;

CREATE TABLE `dates` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '日期表id',
  `time` bigint(20) DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `dates` */

insert  into `dates`(`id`,`time`) values 
(1,1616052991786),
(2,1616051689055),
(3,1616167499896),
(4,1616923253939),
(5,1616930997238),
(6,1617937456107),
(7,1620355960848);

/*Table structure for table `doctor` */

DROP TABLE IF EXISTS `doctor`;

CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '医生id',
  `doctor_Number` varchar(255) DEFAULT NULL COMMENT '医生工号',
  `name` varchar(255) DEFAULT NULL COMMENT '医生姓名',
  `gender` int(11) DEFAULT NULL COMMENT '医生性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `teach_years` int(11) DEFAULT NULL COMMENT '服务年限',
  `graduated_school` varchar(255) DEFAULT NULL COMMENT '毕业院校',
  `tel` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `state` tinyint(1) DEFAULT '0' COMMENT '在线状态',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `doctor` */

insert  into `doctor`(`id`,`doctor_Number`,`name`,`gender`,`age`,`teach_years`,`graduated_school`,`tel`,`email`,`password`,`state`,`gmt_create`,`gmt_modified`) values 
(1,'0420210323','张海峰',0,32,4,'西安邮电大学','15043912738','23456434@qq.com','123456',1,1616513880781,NULL),
(2,'0420210324','王二妞',1,34,2,'清华大学','13279344335','14324352@qq.com','123456',1,1617173378000,NULL),
(3,'0420210325','孙丽丽',1,26,1,'陕西师范大学','14346453425','23655345@qq.com','123456',0,1617173378000,NULL),
(5,'0420210327','徐恒',0,27,1,'西北大学','13423654634','43445663@qq.com','123456',0,1617173378000,NULL);

/*Table structure for table `documents` */

DROP TABLE IF EXISTS `documents`;

CREATE TABLE `documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `url` varchar(255) DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `documents` */

insert  into `documents`(`id`,`creator`,`title`,`content`,`url`,`view_count`,`gmt_create`,`gmt_modified`) values 
(2,'佚名','章仲子论心理学的方法论问题','争鸣（西北师范学院编）1957年7月号第5期上，刊登了章仲子先生“论心理学的方法问题”一文，着重谈到了“自我观察法”在心理学中的应用问题。作者看来：心理学应该采用客观方法可以说是一致的。分歧在于对“自我观察法”在心理学中有无存的权利问题上。\n\n该文作者将近几年来苏联和我国学术界对于这个问题的看法分为三种：\n\n（一）以捷普洛夫为代表的看法（1952年在俄罗斯教育科学院所召开的心理学会议上的发言）认为自我晚察法根本是一种主观的方法，和客观的方法不相容。作为一门科学的心理学，不但不能用它，也不需要它。\n\n（二）以1954年第四期“哲学简通”杂志翻译部发表的“论心理学的哲学问题”的讨论总结为代表，认为自我观察所得来的材料还是有条件地可以用。\n\n（三）中国科学院心理研究所所长潘菽教授在中国心理学会第一届会员代表大会上所作的关于心理学哲学问题的发言（参见心理学报年1956年第一卷第一期），认为自我观察法虽然是主观的方法，但是它可以供给我们一些客观方法所不能得到或不易得到的材料，所以应该把它们用来作为客观方法的补充，以弥补客观方法的不足，可以作为第三种意见的代表。\n\n作者不同意二、三两种意见，而同意第一种看法。对于第三种意见，他认为不“是从唯心主义的原则出发”，就“是受了不可知论的影响......”。对于第二种意见，他说：“除非把自我观察法另作其他解释，它永远是主观主义的、反科学的”。对于第一种看法，作者表示同意。他写道“我认为心理学的方法只能是撤底的客观方法。一切唯物的科学，就其方法论而言，都必须是客观的。心理学是能够单纯依靠客观方法来进行研究的，不需要用主观的自我观察法来补充。”','https://img2.woyaogexing.com/2021/03/27/6e47a9600a8d40e48b3e4a088dd47b81!1242x9999.jpeg',3,1617029266761,1617029266761),
(3,'佚名','亲密关系里的变与不变——米开朗基罗效应','他昨天晚上竟然跟我说，他还是比较喜欢我以前独立的样子。我真的不知道该怎么办……，\n他问我，一开始的那个你怎么不见了？\n当时我就很想说：“当初是谁嫌我太假小子了？是谁要我温柔一些、偶尔撒撒娇？结果现在又嫌我太黏、不够独立？我不懂，真的不懂……”\n她长吁一口气，让落地窗外纷杂的车潮往来，直指心中的慌乱。\n我们的爱到底怎么了？爱情的条件\n\n那么，到底是什么让相爱的人在一起？\n\n新墨西哥大学的Angela Bryan等人的研究指出，我们都想和长得好看、待人温和、社经地位还不错的人发展长久的关系，甚至发生一夜情。当然你可能也听说过相似的人容易彼此吸引，或是性格互补的人，特别喜欢对方身上自己缺乏的性格特征。\n\n遗憾的是，这些理论虽然描绘了我们会被一个人吸引的原因，但是却无法说明：为什么他会“变得”不爱我？如果我的人格、条件都没有太大改变，那么，是什么改变了我们关系？\n\n答案是，这个假设本身就是错误的！多项亲密关系的研究均指出，只要开始谈恋爱，改变就必然会发生；相反地，因为在乎对方，我们会试着调整自己、改变自己的脾气和个性——只是有的伴侣会对对方的改变感到满意；有的，则会永远在抱怨。常见的抱怨形式有2种：\n\n1、抱怨改变型\n\n“你为什么变了？”、“你以前不是这样的”、“你变成一个我几乎不认识的人”、“从前那个温柔的你，怎么不见了？”\n\n这类型的抱怨内容，通常会拿你现在的个性、行为或特质，跟过去的你做对比，并指出很多好的特质已经不如往昔，甚至可能让他怀疑，当初是否看错了人，甚至心生分手之念。\n\n2、抱怨不变型\n\n另一种情况是对方常常提起你没有“按照你们的约定”去做，虽然这些约定，很多时候都是由TA“自己决定”的。譬如：\n\n“你为什么还是一样固执？”、“不是说好会改，为什么还要挑战我的底线？”、“结果你还是一样，我已经不想再跟你吵这个了……”\n\n根据恋爱心理学家Sandra Murray的观点，或许世界上根本不存在所谓的优点或缺点，只有“特点”。\n\n她的几项研究都发现了这样一些被试，虽然他们知道自己的伴侣有些缺点，但还是会替他说话：例如：\n\n    “其实他没有这么糟，只是有时候反应慢一些而已”\n    “他真得挺幼稚的，不过也因为这样，跟他相处的时候非常轻松”\n    “我喜欢他有点gay的样子”\n    “他就是有点呆萌啦……不过如果有一天他突然变得浪漫了，我反而会怀疑他是不是做了什么坏事”。\n\nMurray发现，在这些人的眼中，伴侣的缺点都有了“光明”的色彩，他们能看见伴侣身上的特点，并且由衷地喜欢、享受它。后续的研究也指出，那些长久幸福的情侣能够“一直”维持这份喜爱，并乐享其中，纵使这些“特点”在许多人的眼光中，是很“怪”或“不太讨人喜爱”。\n\n让我们回头看看前面谈到的2种抱怨，其实都是在表达同一件事情：对方不喜欢你现在的样子。\n\n这些不喜欢，并不是因为变或不变，而是是否能够站在当初欣赏的立场上接纳对方所有的特点，你具有什么条件不重要，重要的事是你这个人。那么，你是否需要为对方改变自己呢？根据先前的研究，首先你得知道自己“想不想要”改变，换句话说就是：你想要变成怎样的人？\n\n每个人心中都有一个理想中的形象，例如少抽一点烟、事情不要拖到最后一刻才做、处事更成熟圆融、不要太在乎别人的看法等。只是我们常忙于各种大小琐事，很少考虑自己。\n\n一年之计在于春，抓住春天的尾巴，问问自己真正想要什么？以下的问题可以协助你探索自己。\n\n1、最后悔的事情\n\n试着回想过去一年里，是否有一些你做得不够好的事情？例如公司的业务、与同事的关系、和家人朋友相处时的摩擦等。如果可以重来，你会希望改变哪一段回忆？当初怎样做会更好呢？身边有没有谁可以作为你的榜样？\n\n2、最想改变的习惯或特质\n\n有没有无法摆脱的坏习惯或特质困扰着你，甚至让你产生了深重的负疚感？例如不爱收拾屋子，到处都是乱糟糟、邋里邋遢的样子等。如果不再有这些习惯你会变得更愉快吗？会让你及周围的人更容易接纳你、欣赏你、喜欢你吗？\n\n　\n\n▌爱情里的米开朗基罗效应\n\n一个好的伴侣，应该能看见你的好、了解你的需求、了解你想变成什么样的人，并且持续给你支持、协助，让你一点一滴地变成一个更好的人，这就是心理学家Drigotas所提出的“米开朗基罗效应”。\n\nDrigotas认为恋爱的过程就像是米开朗基罗雕刻戴维像一样，称职的另一半能顺着你的特性，激发你的潜能，让你变得更喜欢自己，而不是只在乎自己的需求，一味地要求你符合TA的要求。\n\n并不是每个人都能幸运地找到一个持续欣赏自己的人，如果你遇见了，请好好珍惜这位雕刻家；如果你一直没遇上，还时常争吵、挨骂、受委屈，那么是时候该问问自己：你喜欢现在的你吗？这是你期待的关系吗？如果可能，你会希望对方怎么样来协助你成长？','https://img2.woyaogexing.com/2021/03/26/697f7eb913e74ef4abd03eb77c2f7abf!1242x9999.jpeg',11,1617029484718,1617029484718),
(4,'佚名','舒心短文','“我独坐须弥山巅，\n\n将万里浮云一眼看穿。\n\n一个人在雪中弹琴，\n\n另一个人在雪中知音。\n\n先是在雪山的两边遥相误解，\n\n然后用一生的时间奔向对方的胸怀。\n\n独行的自在\n\n我行遍世间所有的路，\n\n逆着时光行走，\n\n只为今生与你邂逅……\n\n人生路上，会遇到很多人。\n\n有的人同行一段，\n\n然后就走散了。\n\n有的人毫不起眼，\n\n却可以陪你到终点。','https://img2.woyaogexing.com/2021/03/26/1b700fe8103e4ea18e544f2e960b8da3!1242x9999.jpeg',15,1617029763845,1617937409592),
(5,'细观社会的杜杜','治愈系美文系列','整理了很多“治愈系温暖的”句子，值得你坐下来静静的品读。\n\n\n1、\n\n你年纪轻轻，心地善良，怕什么没人爱。别担心，你终会遇见这样的一个人，好的总是压箱底，所有的不期而遇，只为遇见你 ​​​​。\n\n2、\n\n很多人说生活没那么简单，可是生活本就是一餐一饭，一生专心做好一件事，守着亲人留下的宅院，缝缝补补，在四季风物的更替里缓缓前进的。\n\n——《海街日记》\n\n3、\n\n我想和喜欢的人在喜欢的地方，过自己喜欢的生活，偶尔散散步，吃吃想吃的东西，烤烤面包，希望能将自己感受到的充实感传递给品尝我们面包的顾客们，这边的风景当真每天都别有不同，不只是美丽这么简单。\n\n—— 《幸福的面包》\n\n4、\n\n肚子饿的时候容易心情不好，所以，一定要乖乖吃饭。\n\n——《深夜烘焙坊》\n\n5、\n\n就如同没有永远的幸福，也没有永远的不幸。\n\n——《再见， 总有一天》\n\n6、\n\n如果痛苦的话就别努力了，保持平常心就好了。\n\n——《丈夫得了抑郁症》\n\n7、\n\n至于离开你，那毕竟是很久以后的事。\n\n——《白兔糖》\n\n8、\n\n风景如画，旋律优美。人生就像一次旅程，沿途会遇到各种风景，但最后我们都要回归。\n\n——《菊次郎的夏天》\n\n9、\n\n我想也许是因为和喜欢的人在一起，世界都变成了我们喜欢的样子。\n\n——《在世界中心呼唤爱》\n\n10、\n\n你真正想写的，不是我的故事，而是你自己的故事。我只是害怕，只是害怕面对自己的过去，面对我的未来。\n\n——《第八日的蝉》\n\n11、\n\n如果说爱你，还打你，那一定是说谎;如果爱你，就会像我这样紧紧抱住你。\n\n——《小偷家族》\n\n12、\n\n我知道世界什么时候到末日，那就是我死的时候。世界从我出生的时候开始，所以我死的时候也会一起灭亡。\n\n——《梦旅人》\n\n13、\n\n年轻的时候有过怦然心动的感觉吧。那是什么?这里感觉到疼痛，只能用 “怦”这个字形容，只要想到喜欢的人心就会怦怦跳，这种怦怦跳的机器，过了三十岁就会失灵，特别是开始寻找结婚对象后就不再有了，大概机器已经坏了。\n\n——《盗钥匙的方法》','https://img2.woyaogexing.com/2021/03/26/48b8eef1c1324c10b101df565288a64b!1242x9999.jpeg',16,1617082075929,1618154045335),
(6,'张海峰','治愈受伤的心','今天与朋友交谈中突然明白了一句话，“原来每一个努力去微笑的人，也曾在深夜里痛苦。”那个独行、一个人逛街、看电影，一个人在旅途中享受沿途的风景的人，都有一个被伤的支离破碎的心。只是他最后把碎片收集了起来，粘在一起罢了。\n\n他跟我说的时候，脸上带着微笑。我看得出那背后的伤、狼狈不堪的夜里、四下无人的街中，还有那散落的酒瓶。他笑着告诉我，他也曾遇到了一个让他心动的姑娘，也幻想会有一份相濡以沫的爱情。我知道，从校服到婚纱，从充满青春记忆的校园步入神圣庄严的教堂，是每一个对爱情满怀希冀的少年的憧憬。只是当他在今天谈笑风生的说起那个姑娘的时候，我就明白，他的梦碎在了那年，如镜花水月般幻灭在了那个青葱的岁月里。我听他把故事娓娓道来，言语中尽是沧桑。与其说今天的他把心锁起来了，倒不如说他把心葬在了那个举世无双的好时光里。“年年岁岁花相似，岁岁年年人不同。”跟他一样，我也是一个时常会怀缅过去的人，坐在操场的一角，夕阳的斜晖洒在了高楼白墙上，恍惚之间，仿佛回到了那个风华正茂的高中日子。只是这一切就如林徽因在《别丢掉》中深情的写道：“只有人不见，梦似的挂起。”可转念一想，何必把怀念变得比故事还长，世间事，除了生死，哪一件不是闲事？\n\n还有另外一个朋友，他去年预科的时候，认识了一个很优秀的姑娘，那个姑娘追求了他一年，可他却没有答应。我之前问过他，他跟我说：“她现在在武汉读书，我们两个的家又不在一个地方，一年也见不了几次，何必呢？”我听得出他话语中的心酸与无奈。但今天，是他的生日，那个姑娘在朋友圈中发了一条动态“相隔千里，你在远方要好好照顾好自己，不要忘了那个在十八岁陪了你一年的南方姑娘。后会有期！”并配上了两人亲密的照片，照片中宛如情侣。他看到的时候，眼眶都红了，抱住我说：“怎么办？”那时候我就知道，那个南方的姑娘，铁了心认定他了。原来，最美好的爱情不是每天都腻在一起，也不是每天两人之间都有着浪漫与惊喜，而是彼此之间坚定的选择——即使千险万阻。\n\n其实，男人最大的遗憾，应该就是在最无能为力的年龄，遇到了最想照顾一生的姑娘。','https://img2.woyaogexing.com/2021/03/26/4827b9cb11864742835e6672207bad52!1242x9999.jpeg',33,1617097779467,1621349840226);

/*Table structure for table `documents_and_tags` */

DROP TABLE IF EXISTS `documents_and_tags`;

CREATE TABLE `documents_and_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `document_id` int(11) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `documents_and_tags` */

insert  into `documents_and_tags`(`id`,`document_id`,`tag_id`) values 
(1,1,1),
(3,2,1),
(6,3,2),
(8,4,3),
(9,5,4),
(10,5,2),
(11,6,4),
(12,6,1),
(13,6,2);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '登录表id',
  `account_id` int(20) NOT NULL COMMENT '用户id',
  `account_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户登录密码',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建账户时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改账户时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `login` */

insert  into `login`(`id`,`account_id`,`account_name`,`password`,`gmt_create`,`gmt_modified`) values 
(6,10,'test1','5a105e8b9d40e1329780d62ea2265d8a',1615443310277,NULL),
(7,11,'test2','ad0234829205b9033196ba818f7a872b',1615470504709,NULL),
(8,12,'啦啦啦','e10adc3949ba59abbe56e057f20f883e',1616512596003,NULL),
(9,13,'嘿嘿嘿','e10adc3949ba59abbe56e057f20f883e',1620355862790,NULL);

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题id',
  `content` varchar(255) DEFAULT NULL COMMENT '问题内容',
  `view_count` int(11) DEFAULT NULL COMMENT '访问量',
  `likes` int(11) DEFAULT NULL COMMENT '点赞量',
  `anonymous` tinyint(1) DEFAULT NULL COMMENT '是否匿名',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `question` */

insert  into `question`(`id`,`content`,`view_count`,`likes`,`anonymous`,`status`,`gmt_create`,`gmt_modified`) values 
(1,'test',11,0,0,1,1616039192795,1620835243037),
(2,'test111',4,0,1,1,1616051689055,1620356005998),
(4,'角色2测试提问题',2,0,0,1,1616167499876,1620356029306),
(5,'学生测试提问题功能test1',15,0,0,1,1616923253918,1617083261081),
(6,'学生测试提问题功能test2',5,0,0,0,1616930997215,1616934617160),
(7,'测试提问',1,0,0,1,1617937455821,1617937562378),
(8,'学生测试提问题功能test1',0,0,0,0,1620355960712,1620355960712);

/*Table structure for table `questions_and_tags` */

DROP TABLE IF EXISTS `questions_and_tags`;

CREATE TABLE `questions_and_tags` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '问题和标签中间表id',
  `quest_id` int(11) DEFAULT NULL COMMENT '问题id',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `questions_and_tags` */

insert  into `questions_and_tags`(`id`,`quest_id`,`tag_id`) values 
(1,1,1),
(2,2,1),
(3,1,3),
(4,4,2),
(5,4,4),
(6,4,3),
(8,5,2),
(9,5,1),
(10,7,3);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生表id',
  `stu_number` varchar(255) NOT NULL COMMENT '学号',
  `name` varchar(255) NOT NULL COMMENT '学生名称',
  `gender` int(11) DEFAULT '0' COMMENT '学生性别',
  `age` int(20) DEFAULT NULL COMMENT '学生年龄',
  `tel` varchar(255) DEFAULT NULL COMMENT '学生电话',
  `email` varchar(255) NOT NULL COMMENT '学生邮箱',
  `password` varchar(255) NOT NULL COMMENT '学生登录密码',
  `state` tinyint(1) DEFAULT '0' COMMENT '学生在线状态',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`stu_number`,`name`,`gender`,`age`,`tel`,`email`,`password`,`state`,`gmt_create`,`gmt_modified`) values 
(10,'04172078','test1',0,23,'15043912738','13467347324@qq.com','test1',0,1615443307173,NULL),
(11,'04172079','test2',1,22,'15043912738','23456434@qq.com','test2',0,1615470501602,NULL),
(12,'04172077','啦啦啦',1,22,'15043912738','lizhenfeng@didiglobal.com','123456',1,1616512595945,NULL);

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(255) NOT NULL COMMENT '标签内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tag` */

insert  into `tag`(`id`,`tag_name`) values 
(1,'焦虑'),
(2,'自卑'),
(3,'宿舍'),
(4,'家庭');

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户信息表id',
  `nick_name` varchar(255) NOT NULL COMMENT '别名',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_img` varchar(255) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`nick_name`,`user_id`,`user_img`,`gmt_create`,`gmt_modified`) values 
(1,'test1',10,'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3222454269,3484359568&fm=26&gp=0.jpg',1615443311816,NULL),
(2,'test2',11,'https://gss0.baidu.com/70cFfyinKgQFm2e88IuM_a/forum/w=580/sign=6d09f1971f30e924cfa49c397c096e66/bcb8ada1cd11728b2d1160bdc8fcc3cec2fd2cad.jpg',1615470505902,NULL),
(3,'啦啦啦',12,'https://img2.baidu.com/it/u=1601406830,2108761440&fm=26&fmt=auto&gp=0.jpg',1616512596032,NULL),
(4,'嘿嘿嘿',13,NULL,1620355862846,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
