/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License. */

import { CreateOutline, CopyOutline, PlayOutline, PauseOutline, TrashOutline } from '@vicons/ionicons5'
import {JobStatus} from "@/api/models/cdc/types/cdcJob";
import type {CdcJobDefinition} from "@/api/models/cdc";
const props = {
  row: {
    type: Object as PropType<any>,
    default: {},
  },
}

export default defineComponent({
  name: 'TableAction',
  props,
  emits: ['handleEdit', 'handleRun', 'handleCancel', 'handleDelete', 'handleCopy'],
  setup(props, { emit }) {
    const { t } = useLocaleHooks()
    const dialog = useDialog()
    const message = useMessage()
    const cdcJobStatusMap = reactive<Map<number, string>>(new Map())
    const { mittBus } = getCurrentInstance()!.appContext.config.globalProperties

    const handleEdit = (row: CdcJobDefinition) => {
      emit('handleEdit', row)
    }

    const handleRunOrCancel = (row: CdcJobDefinition) => {
        switch (row.currentStatus) {
            case JobStatus.FRESHED:
            case JobStatus.FINISHED:
            case JobStatus.FAILED:
            case JobStatus.CANCELED:
            case JobStatus.UNKNOWN:
                emit('handleRun', row)
                break
            case JobStatus.RUNNING:
                emit('handleCancel', row)
                break
            default:
                message.warning(t('cdc.cdc_job_exe_not_support'))
        }

    }

    const handleDelete = (row: CdcJobDefinition) => {
        dialog.warning({
            title: t('cdc.confirm_title'),
            content: t('cdc.confirm_delete_content'),
            positiveText: t('cdc.confirm_sure'),
            negativeText: t('cdc.confirm_cancel'),
            onPositiveClick: () => {
                emit('handleDelete', row)
            }
        })
    }

    const handleCopy = (row: CdcJobDefinition) => {
      emit('handleCopy', row)
    }

    const isLoading = (row: CdcJobDefinition) => {
        return row.currentStatus == JobStatus.CANCELLING ||
            row.currentStatus == JobStatus.SUBMITTING
    }

    const getLabel = (row: CdcJobDefinition) => {
        switch (row.currentStatus) {
            case JobStatus.FRESHED:
            case JobStatus.FINISHED:
            case JobStatus.FAILED:
            case JobStatus.CANCELED:
            case JobStatus.UNKNOWN:
                return t('cdc.run')
            case JobStatus.RUNNING:
                return t('cdc.cancel')
            case JobStatus.SUBMITTING:
            case JobStatus.CANCELLING:
            case JobStatus.CREATED:
                return t('cdc.loading')
            default:
                return t('cdc.loading')
        }
    }

    const getIcon = (row: CdcJobDefinition) => {
        switch (row.currentStatus) {
            case JobStatus.UNKNOWN:
            case JobStatus.FRESHED:
            case JobStatus.FINISHED:
            case JobStatus.FAILED:
            case JobStatus.CANCELED:
                return PlayOutline
            case JobStatus.RUNNING:
                return PauseOutline
            default:
                return PlayOutline
        }
    }

    mittBus.on('cdcSubmitConfirmFinish', (data: any) => {
        const id = data.id
        cdcJobStatusMap.set(id, JobStatus.CANCELLING)
    })

    return {
      t,
      handleEdit,
      handleRunOrCancel,
      handleDelete,
      handleCopy,
      isLoading,
      getLabel,
      getIcon,
    }
  },
  render() {
    return (
        <n-space>
            <n-tooltip trigger="hover">
                {{
                    default: () => this.getLabel(this.row),
                    trigger: () => (
                        <n-button
                            loading={this.isLoading(this.row)}
                            size="small"
                            type="primary"
                            onClick={() =>
                                this.handleRunOrCancel(this.row)}
                            circle
                        >
                            <n-icon component={this.getIcon(this.row)}/>
                        </n-button>
                    ),
                }}
            </n-tooltip>
            <n-tooltip trigger="hover">
                {{
                    default: () => this.t('cdc.edit'),
                    trigger: () => (
                        <n-button
                            size="small"
                            type="primary"
                            onClick={() =>
                                this.handleEdit(this.row)}
                            circle
                        >
                            <n-icon component={CreateOutline}/>
                        </n-button>
                    ),
                }}
            </n-tooltip>
            <n-tooltip trigger="hover">
                {{
                    default: () => this.t('cdc.copy'),
                    trigger: () => (
                        <n-button
                            size="small"
                            type="primary"
                            onClick={() =>
                                this.handleCopy(this.row)}
                            circle
                        >
                            <n-icon component={CopyOutline}/>
                        </n-button>
                    ),
                }}
            </n-tooltip>
            <n-tooltip trigger="hover">
                {{
                    default: () => this.t('cdc.delete'),
                    trigger: () => (
                        <n-button
                            size="small"
                            type="error"
                            onClick={() =>
                                this.handleDelete(this.row)}
                            circle
                        >
                            <n-icon component={TrashOutline}/>
                        </n-button>
                    ),
                }}
            </n-tooltip>

        </n-space>
    )
  },
})
