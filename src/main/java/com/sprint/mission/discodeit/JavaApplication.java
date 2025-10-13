package com.sprint.mission.discodeit;

@Deprecated
public class JavaApplication {

  //static String fileDirectory = FILE_PATH;
  //static final String strongPassword = "fe5A3sad@lks^";

  public static void main(String[] args) {

        /*testJcfUserService();
        testJcfChannelService();
        testJcfMessageService();
        testFileUserService();
        testFileChannelService();
        testFileMessageService();
        testBasicUserService();
        testBasicChannelService();
        testBasicMessageService();*/

  }

    /*public static void testJcfUserService() {

        UserService jcfUserCrudService = new JCFUserService();

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User.Builder()
                .username("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .username("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        jcfUserCrudService.createUser(userOne);
        jcfUserCrudService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                .orElseThrow((IllegalArgumentException::new)).toString());
        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        DiscordDTO.UpdateUserRequest requestOne = DiscordDTO.UpdateUserRequest.builder()
                .id(userOne.getId())
                .username(userOne.getNickname())
                .email(userOne.getEmail())
                .currentPassword(strongPassword)
                .newPassword(strongPassword + "k")
                .description("Bye")
                .build();
        jcfUserCrudService.updateUser(requestOne);
        System.out.println(userOne.getNickname() + " 정보 업데이트");
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        jcfUserCrudService.deleteUserById(userTwo.getId());
        System.out.println(userTwo.getNickname() + " 유저 삭제");
        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

    }

    public static void testFileUserService() {

        UserService fileUserCrudService = new FileUserService(Path.of(fileDirectory + "users"));

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User.Builder()
                .username("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .username("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        fileUserCrudService.createUser(userOne);
        fileUserCrudService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(fileUserCrudService.findUserById(userOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        DiscordDTO.UpdateUserRequest requestOne = DiscordDTO.UpdateUserRequest.builder()
                .id(userOne.getId())
                .username(userOne.getNickname())
                .email(userOne.getEmail())
                .currentPassword(strongPassword)
                .newPassword(strongPassword + "k")
                .description("Bye")
                .build();
        fileUserCrudService.updateUser(requestOne);
        System.out.println(userOne.getNickname() + " 정보 업데이트");
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(fileUserCrudService.findUserById(userOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        fileUserCrudService.deleteUserById(userOne.getId());
        fileUserCrudService.deleteUserById(userTwo.getId());
        System.out.println(userTwo.getNickname() + " 유저 삭제");
        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //clear
        fileUserCrudService.findAllUsers()
                .forEach(user -> fileUserCrudService.deleteUserById(user.getId()));

    }

    public static void testBasicUserService() {

        //UserRepository jcfUserRepository = new JCFUserRepository();
        UserRepository fileUserRepository = new FileUserRepository();
        UserService basicUserCrudService = new BasicUserService(fileUserRepository, new Validator());

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User.Builder()
                .username("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .username("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        basicUserCrudService.createUser(userOne);
        basicUserCrudService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(basicUserCrudService.findUserById(userOne.getId())
                .orElseThrow((IllegalArgumentException::new)).toString());
        System.out.println("유저 목록 읽기");
        basicUserCrudService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        DiscordDTO.UpdateUserRequest requestOne = DiscordDTO.UpdateUserRequest.builder()
                .id(userOne.getId())
                .username(userOne.getNickname())
                .email(userOne.getEmail())
                .currentPassword(strongPassword)
                .newPassword(strongPassword + "k")
                .description("Bye")
                .build();
        basicUserCrudService.updateUser(requestOne);
        System.out.println(userOne.getNickname() + " 정보 업데이트");
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(basicUserCrudService.findUserById(userOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        basicUserCrudService.deleteUserById(userTwo.getId());
        System.out.println(userTwo.getNickname() + " 유저 삭제");

        System.out.println("유저 목록 읽기");
        basicUserCrudService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

        //clear
        basicUserCrudService.findAllUsers()
                .forEach(user -> basicUserCrudService.deleteUserById(user.getId()));

    }

    public static void testJcfChannelService() {

        ChannelService jcfChannelCrudService = new JCFChannelService();

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test@test.com", strongPassword, "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        //Channel channelOne = new Channel("channelOne", ChannelType.TEXT, false);
        //Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
        Channel channelOne = new Channel.Builder()
                .name("channelOne")
                .type(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Channel channelTwo = new Channel.Builder()
                .name("channelTwo")
                .type(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        jcfChannelCrudService.createChannel(channelOne);
        //jcfChannelCrudService.addUserToChannel(channelOne.getId(), user);
        //jcfChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        jcfChannelCrudService.createChannel(channelTwo);
        //jcfChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        //jcfChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        System.out.println(channelOne.getChannelName() + " 채널 읽기");
        System.out.println(jcfChannelCrudService.findChannelById(channelOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        DiscordDTO.UpdateChannelRequest requestTwo = DiscordDTO.UpdateChannelRequest.builder()
                .id(channelTwo.getId())
                .name(channelTwo.getChannelName())
                .type(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        jcfChannelCrudService.updateChannel(requestTwo);
        System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        System.out.println(channelTwo.getChannelName() + " 채널 읽기");
        System.out.println(jcfChannelCrudService.findChannelById(channelTwo.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        //System.out.println(channelOne.getChannelName() + " 에서 "
                //+ channelOne.getUserMap().get(user.getId()).getNickname() + " 유저 삭제");
        //jcfChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        //jcfChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        jcfChannelCrudService.deleteChannelById(channelTwo.getId());
        System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

    }

    public static void testFileChannelService() {

        ChannelService fileChannelCrudService = new FileChannelService(Path.of(fileDirectory + "channels"));

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test@test.com", strongPassword, "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        Channel channelOne = new Channel.Builder()
                .name("channelOne")
                .type(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Channel channelTwo = new Channel.Builder()
                .name("channelTwo")
                .type(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        fileChannelCrudService.createChannel(channelOne);
        //fileChannelCrudService.addUserToChannel(channelOne.getId(), user);
        //fileChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        fileChannelCrudService.createChannel(channelTwo);
        //fileChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        //fileChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        System.out.println(channelOne.getChannelName() + " 채널 읽기");
        System.out.println(fileChannelCrudService.findChannelById(channelOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        DiscordDTO.UpdateChannelRequest requestTwo = DiscordDTO.UpdateChannelRequest.builder()
                .id(channelTwo.getId())
                .name(channelTwo.getChannelName())
                .type(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        fileChannelCrudService.updateChannel(requestTwo);
        System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        channelOne = fileChannelCrudService.findChannelById(channelOne.getId())
                .orElseThrow(IllegalArgumentException::new);
        channelTwo = fileChannelCrudService.findChannelById(channelTwo.getId())
                .orElseThrow(IllegalArgumentException::new);
        System.out.println(channelTwo.getChannelName() + " 채널 읽기");
        System.out.println(fileChannelCrudService.findChannelById(channelTwo.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        //System.out.println(channelOne.getChannelName() + " 에서 "
                //+ channelOne.getUserMap().get(user.getId()).getNickname() + " 유저 삭제");
        //fileChannelCrudService.deleteUserFromChannel(channelOne.getId(), user.getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        //fileChannelCrudService.deleteMessageFromChannel(channelOne.getId(), message.getId());
        //channelOne.getMessageMap().entrySet().forEach(m -> System.out.println(m.toString()));
        fileChannelCrudService.deleteChannelById(channelTwo.getId());
        System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //clear
        fileChannelCrudService.findAllChannels()
                .forEach(channel -> fileChannelCrudService.deleteChannelById(channel.getId()));

    }

    public static void testBasicChannelService() {

        //ChannelRepository jcfChannelRepository = new JCFChannelRepository();
        ChannelRepository fileChannelRepository = new FileChannelRepository();
        ChannelService basicChannelCrudService = new BasicChannelService(fileChannelRepository);

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test@test.com", strongPassword, "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        Channel channelOne = new Channel.Builder()
                .name("channelOne")
                .type(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Channel channelTwo = new Channel.Builder()
                .name("channelTwo")
                .type(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        basicChannelCrudService.createChannel(channelOne);
        //basicChannelCrudService.addUserToChannel(channelOne.getId(), user);
        //basicChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        basicChannelCrudService.createChannel(channelTwo);
        //basicChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        //basicChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        System.out.println(channelOne.getChannelName() + " 채널 읽기");
        System.out.println(basicChannelCrudService.findChannelById(channelOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("채널 목록 읽기");
        basicChannelCrudService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        DiscordDTO.UpdateChannelRequest requestTwo = DiscordDTO.UpdateChannelRequest.builder()
                .id(channelTwo.getId())
                .name(channelTwo.getChannelName())
                .type(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        basicChannelCrudService.updateChannel(requestTwo);
        System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        channelTwo = basicChannelCrudService.findChannelById(channelTwo.getId())
                .orElseThrow(() ->  new IllegalArgumentException("No such channels"));
        System.out.println(channelTwo.getChannelName() + " 채널 읽기");
        System.out.println(basicChannelCrudService.findChannelById(channelTwo.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        channelOne = basicChannelCrudService.findChannelById(channelOne.getId())
                        .orElseThrow(() -> new IllegalArgumentException("No such users."));
        System.out.println(channelOne.getChannelName() + " 에서 "
                + user.getNickname() + " 유저 삭제");
        //basicChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        //basicChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        basicChannelCrudService.deleteChannelById(channelTwo.getId());
        System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        System.out.println("채널 목록 읽기");
        basicChannelCrudService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //clear
        basicChannelCrudService.findAllChannels()
                .forEach(channel -> basicChannelCrudService.deleteChannelById(channel.getId()));

    }

    public static void testJcfMessageService() {

        UserService jcfUserCrudService = new JCFUserService();
        ChannelService jcfChannelCrudService = new JCFChannelService();
        MessageService jcfMessageCrudService = new JCFMessageService(jcfUserCrudService, jcfChannelCrudService);

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", strongPassword, "Hi");
        jcfUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", strongPassword, "Hi");
        jcfUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.DM, false);
        jcfChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
        jcfChannelCrudService.createChannel(channelTwo);
        //Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        //Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
        Message messageOne = new Message.Builder()
                .authorId(userOne.getId())
                .channelId(channelOne.getId())
                .content("messageOne")
                .isReply(false)
                .parentMessageId(null)
                .build();
        Message messageTwo = new Message.Builder()
                .authorId(userTwo.getId())
                .channelId(channelTwo.getId())
                .content("messageTwo")
                .isReply(true)
                .parentMessageId(messageOne.getId())
                .build();
        jcfMessageCrudService.createMessage(messageOne);
        jcfMessageCrudService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        jcfMessageCrudService.findChildMessagesById(messageOne.getId())
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        jcfMessageCrudService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        DiscordDTO.UpdateMessageRequest requestOne = DiscordDTO.UpdateMessageRequest.builder()
                .id(messageOne.getId())
                .content("messageOne edited")
                .isReply(messageOne.isReply())
                .parentMessageId(messageOne.getParentMessageId())
                .build();
        jcfMessageCrudService.updateMessage(requestOne);
        System.out.println(messageOne.getId() + " 정보 업데이트");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        jcfMessageCrudService.deleteMessageById(messageTwo.getId());
        System.out.println(messageTwo.getId() + " 메시지 삭제");
        System.out.println("메시지 목록 읽기");
        jcfMessageCrudService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

    }

    public static void testFileMessageService() {

        UserService fileUserCrudService = new FileUserService(Path.of(fileDirectory + "users"));
        ChannelService fileChannelCrudService = new FileChannelService(Path.of(fileDirectory + "channels"));
        MessageService fileMessageCrudService = new FileMessageService(Path.of(fileDirectory + "messages"), fileUserCrudService, fileChannelCrudService);

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon3@gmail.com", strongPassword, "Hi");
        fileUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon4@gmail.com", strongPassword, "Hi");
        fileUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.FORUM, false);
        fileChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.DM, true);
        fileChannelCrudService.createChannel(channelTwo);
        Message messageOne = new Message.Builder()
                .authorId(userOne.getId())
                .channelId(channelOne.getId())
                .content("messageOne")
                .isReply(false)
                .parentMessageId(null)
                .build();
        Message messageTwo = new Message.Builder()
                .authorId(userTwo.getId())
                .channelId(channelTwo.getId())
                .content("messageTwo")
                .isReply(true)
                .parentMessageId(messageOne.getId())
                .build();
        fileMessageCrudService.createMessage(messageOne);
        fileMessageCrudService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        fileMessageCrudService.findChildMessagesById(messageOne.getId())
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        DiscordDTO.UpdateMessageRequest requestOne = DiscordDTO.UpdateMessageRequest.builder()
                .id(messageOne.getId())
                .content("messageOne edited")
                .isReply(messageOne.isReply())
                .parentMessageId(messageOne.getParentMessageId())
                .build();
        fileMessageCrudService.updateMessage(requestOne);
        System.out.println(messageOne.getId() + " 정보 업데이트");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        fileMessageCrudService.deleteMessageById(messageTwo.getId());
        System.out.println(messageTwo.getId() + " 메시지 삭제");
        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //clear
        fileMessageCrudService.findAllMessages()
                .forEach(message -> fileMessageCrudService.deleteMessageById(message.getId()));

    }

    public static void testBasicMessageService() {

        UserRepository jcfUserRepository = new JCFUserRepository();
        ChannelRepository jcfChannelRepository = new JCFChannelRepository();
        //MessageRepository jcfMessageRepository = new JCFMessageRepository();
        MessageRepository fileMessageRepository = new FileMessageRepository();
        UserService basicUserCrudService = new BasicUserService(jcfUserRepository, new Validator());
        ChannelService basicChannelCrudService = new BasicChannelService(jcfChannelRepository);
        MessageService basicMessageCrudService = new BasicMessageService(fileMessageRepository, jcfUserRepository, jcfChannelRepository);

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", strongPassword, "Hi");
        basicUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", strongPassword, "Hi");
        basicUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.DM, false);
        basicChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
        basicChannelCrudService.createChannel(channelTwo);
        Message messageOne = new Message.Builder()
                .authorId(userOne.getId())
                .channelId(channelOne.getId())
                .content("messageOne")
                .isReply(false)
                .parentMessageId(null)
                .build();
        Message messageTwo = new Message.Builder()
                .authorId(userTwo.getId())
                .channelId(channelTwo.getId())
                .content("messageTwo")
                .isReply(true)
                .parentMessageId(messageOne.getId())
                .build();
        basicMessageCrudService.createMessage(messageOne);
        basicMessageCrudService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(basicMessageCrudService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        basicMessageCrudService.findChildMessagesById(messageOne.getId())
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        basicMessageCrudService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        DiscordDTO.UpdateMessageRequest requestOne = DiscordDTO.UpdateMessageRequest.builder()
                .id(messageOne.getId())
                .content("messageOne edited")
                .isReply(messageOne.isReply())
                .parentMessageId(messageOne.getParentMessageId())
                .build();
        basicMessageCrudService.updateMessage(requestOne);
        System.out.println(messageOne.getId() + " 정보 업데이트");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(basicMessageCrudService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        basicMessageCrudService.deleteMessageById(messageTwo.getId());
        System.out.println(messageTwo.getId() + " 메시지 삭제");
        System.out.println("메시지 목록 읽기");
        basicMessageCrudService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

        //clear
        basicMessageCrudService.findAllMessages()
                .forEach(message -> basicMessageCrudService.deleteMessageById(message.getId()));
        basicChannelCrudService.findAllChannels()
                .forEach(channel -> basicChannelCrudService.deleteChannelById(channel.getId()));
        basicUserCrudService.findAllUsers()
                .forEach(user -> basicUserCrudService.deleteUserById(user.getId()));

    }*/

}
