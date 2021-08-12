package com.project.localbatter.imageupload;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTestClass {
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BoardService boardService;
//
//    private final static Logger log = LogManager.getLogger();
//
//    @Test
//    public void 거래요청_게시글_저장(){
//
//        String user = "1";
//        String seller = "2";
//        Long boardId = 1L;
//
//        BoardEntity board = boardService.findBoard(boardId);
//
//        UserEntity sellerEntity = userService.findUserOne(seller);
//        UserEntity buyerEntity = userService.findUserOne(user);
//
//        TransactionEntity transactionEntity = TransactionEntity.builder()
//        .boardId(board)
//        .seller(sellerEntity)
//        .buyer(buyerEntity)
//        .build();
//
//        transactionRepository.save(transactionEntity);
//    }
//
//    @Test
//    void 채팅방_중복개설_확인_테스트(){
//
//        UserJoinRoomEntity room = null;
//
//        Optional<UserEntity> user = userRepository.findById(3L);
//        Optional<UserEntity> target = userRepository.findById(2L);
//
//        List<UserJoinRoomEntity> list = chatRoomRepository.findAllByuserVoOrTarget(user.get(), user.get());
//
//        for(UserJoinRoomEntity item : list){
//            if(item.getTarget().equals(target.get())){
//                room = item;
//                break;
//            }
//        }
//
//        if(room != null)
//            log.info("채팅방 존재");
//        else
//            log.info("채팅방 개설");
//
//    }

}
