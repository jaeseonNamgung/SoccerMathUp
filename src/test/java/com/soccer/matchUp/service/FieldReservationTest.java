package com.soccer.matchUp.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FieldReservationTest {

//    @InjectMocks
//    private FieldReservationService reservationSvc;
//    @Mock
//    private FieldReservationRepository reservationRepo;
//
//    public static FieldRequest getFieldDto(){
//        return FieldRequest.of(
//                "접수중",  "○ [평일] (주간) 마포 난지천 인조잔디축구장","유료",
//                "서울특별시 산악문화체험센터>난지천인조잔디축구장", "https://yeyak.seoul.go.kr/web/reservation/selectReservView.do?rsv_svc_id=S210401100008601453",
//                LocalDateTime.parse("2023-05-30 12:00:00.0"),LocalDateTime.parse("2023-07-30 11:07:00.0"),
//                "마포구", 	"https://yeyak.seoul.go.kr/web/common/file/FileDown.do?file_id=1617239932085QQ2HH7Q6BFUUKKLU129S2081E",
//                "1. 공공시설 예약서비스 이용시 필수 준수사항모든 서비스의 이용은 담당 기관의 규정에 따릅니다.","02-3153-9874","이용일","1",
//                LocalDateTime.parse("18:00"), LocalDateTime.parse("22:00"),
//                LocalDateTime.parse("2022-01-01 00:00:00.0"), LocalDateTime.parse("2023-12-31 00:00:00.0")
//        );
//    }
    
    
//    @Test
//    public void getFieldReservationDetailInformationTest()throws Exception{
//        // given
//        given(reservationRepo.findById(anyLong()))
//                .willReturn(
//                        Optional.of(FieldReservation.builder()
//                                        .reservationDate(LocalDateTime.of(2023, 06, 23, 0, 0, 0))
//                                        .field(null)
//                                .build())
//                );
//        // when
//        List<FieldReservationRequest> frDto = reservationSvc.getFieldReservation(getFieldDto());
//        // then
//        assertThat(frDto).extracting("reservationDate").containsExactly("2023-06-23T0:0:0");
//        then(reservationRepo).should().findById(anyLong());
//    }
    

}