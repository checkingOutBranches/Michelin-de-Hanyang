using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using TMPro; // TextMeshPro 네임스페이스 추가

public class BusinessController : MonoBehaviour
{
    public Button businessButton;
    public TextMeshProUGUI statusText; // Text 대신 TextMeshProUGUI 사용
    public GameObject messageText; // 영업 시작 메시지를 담고 있는 Text 오브젝트 (TextMeshProUGUI일 수 있음)

<<<<<<< HEAD
    [SerializeField] // Inspector에서 설정할 수 있게 합니다.
    private CustomerManager customerManager; // CustomerManager 참조 추가
=======
    [SerializeField]
    private CustomerManager customerManager;
    [SerializeField]
    private QuestComplete questComplete;
    [SerializeField]
    private BusinessResult businessResult;
    [SerializeField]
    private RestaurantTimer timer;
    [SerializeField]
    private GameObject timerUI;
>>>>>>> BEDev

    private bool isBusinessOpen = false;
    void Start()
    {
        // 버튼 클릭 이벤트에 함수 연결
        businessButton.onClick.AddListener(ToggleBusinessStatus);
    }

    void ToggleBusinessStatus()
{
    isBusinessOpen = !isBusinessOpen;

    if (isBusinessOpen)
    {
        businessButton.GetComponentInChildren<TextMeshProUGUI>().text = "영업 종료";
        businessButton.transform.localScale = new Vector3(0.8f, 0.8f, 1);
        StartCoroutine(ShowMessage());
        customerManager.SetBusinessStatus(true); // NPC 생성 시작
    }
    else
    {
        businessButton.GetComponentInChildren<TextMeshProUGUI>().text = "영업 시작";
<<<<<<< HEAD
        businessButton.transform.localScale = Vector3.one;
        customerManager.SetBusinessStatus(false); // NPC 생성 중단
=======
        StartCoroutine(ShowMessage("영업이 종료되었습니다."));
        customerManager.SetBusinessStatus(false);
        timer.ForcedFinish();
        businessResult.AfterBusinessProcess();
>>>>>>> BEDev
    }
}
    IEnumerator ShowMessage()
    {
        messageText.SetActive(true); // 메시지 표시
        yield return new WaitForSeconds(1); // 2초간 대기
        messageText.SetActive(false); // 메시지 숨김
    }
}
