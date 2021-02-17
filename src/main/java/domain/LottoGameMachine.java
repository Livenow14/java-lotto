package domain;

import domain.budget.Budget;
import domain.lotto.Lotto;
import domain.lotto.LottoCount;
import domain.lotto.Lottos;
import util.RandomLottoUtil;
import view.LottoGameScreen;
import view.dto.LottoCountResponseDto;
import view.dto.LottoResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LottoGameMachine {
    private static final Budget LOTTO_COST = Budget.amounts(1000);

    private final Budget budget;
    private Lottos lottos;

    public LottoGameMachine(final Budget budget) {
        this.budget = budget;
    }

    public void gameStart() {
        LottoCount lottoCount = calculateLottoCount();
        LottoGameScreen lottoGameScreen = new LottoGameScreen();
        lottoGameScreen.showLottoCount(new LottoCountResponseDto(lottoCount.getLottoCount()));

        lottos = makeLottos(lottoCount);
        List<Lotto> lottoGroup = lottos.getLottos();

        List<LottoResponseDto> lottoResponseDtos = makeLottoResponseDtos(lottoGroup);
        lottoGameScreen.showAllLottoStatus(lottoResponseDtos);
    }

    private List<LottoResponseDto> makeLottoResponseDtos(final List<Lotto> lottoGroup) {
        List<LottoResponseDto> lottoResponseDtos = lottoGroup.stream()
                .map(lotto -> (new LottoResponseDto(lotto)))
                .collect(Collectors.toList());
        return lottoResponseDtos;
    }

    private Lottos makeLottos(final LottoCount lottoCount) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < lottoCount.getLottoCount(); i++) {
            lottos.add(new Lotto(RandomLottoUtil.generateLottoNumbers()));
        }
        return new Lottos(lottos);
    }

    private LottoCount calculateLottoCount() {
        int lottoCount = budget.intQuotient(LOTTO_COST);
        return LottoCount.of(lottoCount);
    }
}
