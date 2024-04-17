using TMPro;
using UnityEngine;

public class InventoryBottom : MonoBehaviour
{
    public TMP_Text bag_capacity;
    public TMP_Text money_script;
    public void SetBottomScript(int money)
    {
        bag_capacity.text = "001/010";
        money_script.text = money + "³É";
    }
}
