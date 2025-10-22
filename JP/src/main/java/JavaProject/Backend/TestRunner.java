package JavaProject.Backend;

import JavaProject.Backend.Situation;
import JavaProject.Backend.SituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

@Component // 이 클래스를 Spring이 관리하는 컴포넌트로 등록
public class TestRunner implements CommandLineRunner {

    // Spring이 자동으로 SituationRepository의 구현체를 주입해 줍니다.
    @Autowired
    private SituationRepository situationRepository;

    @Override
    public void run(String... args) throws Exception {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("[테스트할 항목을 선택하세요.]");
    	System.out.println("1. MongoDB 연결 테스트");
    	System.out.println("2. 데이터 삭제 테스트");
    	System.out.print("select: ");
    	int selectTest = scanner.nextInt();
    	switch(selectTest) {
    	case 1:
    		System.out.println("--- MongoDB 연결 테스트 시작 ---");

            // 1. 새로운 Situation 객체 생성
            Situation newSituation = new Situation();
            newSituation.setTitle("상황 제목입니다~");
            newSituation.setDescription("상황의 내용입니다.");

            // 2. 데이터베이스에 저장 (Insert)
            situationRepository.save(newSituation);
            System.out.println(">> 데이터 저장 성공!");

            // 3. 데이터베이스에서 모든 데이터 조회 (Find)
            List<Situation> situations = situationRepository.findAll();
            
            System.out.println(">> 저장된 데이터 전체 조회:");
            for (Situation situation : situations) {
                System.out.println(situation.toString());
            }

            System.out.println("--- MongoDB 연결 테스트 종료 ---");
    		break;
    	case 2:
    		System.out.println("--- 데이터 삭제 테스트 시작 ---");

    	    // 1. 테스트를 위해 기존 데이터를 모두 삭제하고 시작하겠습니다.
    	    System.out.println("\n[1] 모든 데이터 삭제 (초기화)");
    	    situationRepository.deleteAll();

    	    // 2. 테스트용 데이터 3개 생성 및 저장
    	    System.out.println("\n[2] 테스트용 데이터 3개 저장");
    	    situationRepository.save(new Situation("아르바이트 임금 체불", "최저시급 미달 시 대응법"));
    	    Situation contractSituation = situationRepository.save(new Situation("전세 계약 시 주의사항", "확정일자, 전입신고 필수"));
    	    situationRepository.save(new Situation("중고 거래 사기 대처법", "경찰서 방문 및 진정서 접수"));
    	    
    	    System.out.println(">> 저장 후 전체 데이터:");
    	    situationRepository.findAll().forEach(System.out::println);

    	    // 3. ID로 특정 데이터 삭제하기
    	    System.out.println("\n[3] '전세 계약' 데이터를 ID로 삭제");
    	    String idToDelete = contractSituation.getId(); // 저장된 객체에서 ID를 가져옵니다.
    	    situationRepository.deleteById(idToDelete);

    	    System.out.println(">> ID로 삭제 후 전체 데이터:");
    	    situationRepository.findAll().forEach(System.out::println);

    	    // 4. Title 필드로 특정 데이터 삭제하기
    	    System.out.println("\n[4] '중고 거래 사기 대처법' 데이터를 Title로 삭제");
    	    long deletedCount = situationRepository.deleteByTitle("중고 거래 사기 대처법");
    	    System.out.println(">> Title로 삭제된 문서 개수: " + deletedCount);

    	    System.out.println(">> Title로 삭제 후 전체 데이터:");
    	    situationRepository.findAll().forEach(System.out::println);

    	    // 5. 남은 데이터 모두 삭제하기
    	    System.out.println("\n[5] 남은 데이터 모두 삭제");
    	    situationRepository.deleteAll();
    	    
    	    System.out.println(">> 최종 전체 데이터:");
    	    List<Situation> finalList = situationRepository.findAll();
    	    if (finalList.isEmpty()) {
    	        System.out.println(">> 모든 데이터가 성공적으로 삭제되었습니다.");
    	    }

    	    System.out.println("\n--- 데이터 삭제 테스트 종료 ---");
    		break;
    	default:
    		System.out.println("잘못된 선택입니다.");
    		break;
    	}
    	
        scanner.close();
}

}