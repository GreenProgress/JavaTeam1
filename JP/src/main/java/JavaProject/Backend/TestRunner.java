package JavaProject.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private SituationRepository situationRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("--- 자동 데이터 생성 시작 ---");

        // 1. 테스트용 데이터 하나 만들기
        Situation newSituation = new Situation();
        newSituation.setTitle("자동 생성된 상황 제목");
        newSituation.setDescription("자동 생성된 상황 설명");

        // 2. 저장
        situationRepository.save(newSituation);
        System.out.println(">> 자동 데이터 삽입 완료");

        // 3. 전체 목록 출력
        System.out.println(">> 현재 모든 Situation:");
        situationRepository.findAll().forEach(System.out::println);

        System.out.println("--- 자동 데이터 생성 종료 ---");
    }
}
