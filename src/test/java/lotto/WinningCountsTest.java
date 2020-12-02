package lotto;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WinningCountsTest {

    @DisplayName(value = "티켓 여러장의 당첨 갯수 테스트")
    @Test
    void 당첨_갯수() {
        // given
        List<Integer> ticket = Arrays.asList(1, 2, 3, 4, 5, 6);
        LottoTicket lottoTicket = LottoTicket.newTicket(ticket);
        List<Integer> lastWeeksWinningNumbers = Arrays.asList(1, 2, 3, 10, 11, 12);

        // when
        int purchaseNumber = 2;
        LottoTicketCreatable lottoTicketCreatable = new LottoTicketCreatable() {
            @Override
            public LottoTicket createTicket() {
                return lottoTicket;
            }

            @Override
            public LottoTickets createTickets(int purchaseNumber) {
                List<LottoTicket> ticketList = new ArrayList<>();
                for (int i = 0; i < purchaseNumber; i++) {
                    ticketList.add(createTicket());
                }

                return LottoTickets.newTickets(ticketList);
            }
        };
        LottoTickets lottoTickets = lottoTicketCreatable.createTickets(purchaseNumber);
        WinningCounts winningCounts = new WinningCounts().calculateWinningCount(lottoTickets, lastWeeksWinningNumbers);

        List<Integer> expectTicket = Arrays.asList(1, 2, 3, 4, 5, 6);
        LottoTicket expectLottoTicket1 = LottoTicket.newTicket(expectTicket);
        LottoTicket expectLottoTicket2 = LottoTicket.newTicket(expectTicket);

        WinningCount winningCount1 = new WinningCount(0);
        winningCount1 = winningCount1.increaseCountManager(expectLottoTicket1, lastWeeksWinningNumbers);
        WinningCount winningCount2 = new WinningCount(0);
        winningCount2 = winningCount2.increaseCountManager(expectLottoTicket2, lastWeeksWinningNumbers);


        List<WinningCount> expectWinningCountList = new ArrayList<>();
        expectWinningCountList.add(winningCount1);
        expectWinningCountList.add(winningCount2);
        WinningCounts expect = new WinningCounts(expectWinningCountList);

        // then
        assertThat(winningCounts).isEqualTo(expect);
    }
}